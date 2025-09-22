import { MinusCircleOutlined, PlusOutlined } from "@ant-design/icons";
import { Edit, useForm, useSelect } from "@refinedev/antd";
import { useApiUrl, useCustom, useNotification, useResourceParams } from "@refinedev/core";
import {
  Button,
  Descriptions,
  Divider,
  Form,
  Input,
  InputNumber,
  Popconfirm,
  Popover,
  Select,
  Space,
  Tag,
} from "antd";
import { useEffect, useState } from "react";
import { CiWarning } from "react-icons/ci";

const { TextArea } = Input;

export const StockTakingEdit = () => {
  const [items, setItems] = useState([]);
  const [headerInfo, setHeaderInfo] = useState<any[]>([]);
  const [processIsLoading, setProcessIsLoading] = useState(false);
  const [processNeedConfirm, setProcessNeedConfirm] = useState(false);
  const [stockTakingOpen, setStockTakingOpen] = useState(false);

  const { open } = useNotification();

  const {
    formProps: formPropsEdit,
    saveButtonProps: saveButtonPropsEdit,
    form,
    query,
    onFinish,
  } = useForm({ redirect: "edit" });
  const { data, isLoading } = query;

  const rowIsProcessed = (name: any) => {
    const rowValue = form?.getFieldsValue(["items", name, "status"]);
    if (rowValue.status === "PROCESSED") return true;
    return false;
  };

  const rowHasError = (name: any) => {
    const rowValue = form?.getFieldsValue(["items", name, "error", "message"]);
    return {
      hasError: rowValue.error,
      message: rowValue.message,
    };
  };

  const getStatusTag = (status: string) => {
    let color;
    let text;

    switch (status) {
      case "OPEN":
        color = "orange";
        text = "Criado";
        break;
      case "PARTIALLY_PROCESSED":
        color = "red";
        text = "Parcialmente processado";
        break;
      case "CLOSED":
        color = "green";
        text = "Fechado";
        break;
      case "CANCELED":
        color = "gray";
        text = "Cancelado";
        break;
    }

    return <Tag color={color}>{text}</Tag>;
  };

  const { selectProps: productSelectProps, query: pdQuery } = useSelect({
    resource: "products",
    optionLabel: "description",
    optionValue: "id",
    pagination: {
      mode: "server",
    },
  });
  const { isLoading: pdIsLoading } = pdQuery;

  const { selectProps: warehouseSelectProps, query: whQuery } = useSelect({
    resource: "warehouses",
    optionLabel: "name",
    optionValue: "id",
    pagination: {
      mode: "client",
    },
  });
  const { isLoading: whIsLoading } = whQuery;

  const validateItemsFilled = async () => {
    try {
      const values = await form.validateFields(); // validate
      await onFinish(values); // refine will call update() for you
    } catch (error) {
      // console.error("Validation failed:", error);
      return;
    }

    // validate if all items are filled with counted quantity.
    let unfilled = 0;
    const fvs = form?.getFieldsValue(true);
    console.log(fvs.items);

    fvs.items.forEach((element) => {
      if (element.countedQuantity === null) {
        unfilled++;
      }
    });

    if (unfilled === items.length) {
      // should fill at least one
      open?.({
        type: "error",
        message: "Preencha a contagem de um item, ao menos, antes de processar inventário.",
        description: "Contagem não preenchida",
      });
      return;
    }

    if (unfilled !== 0) {
      setProcessNeedConfirm(true);
    }
  };

  const cancelProcess = () => {
    setProcessNeedConfirm(false);
  };

  const apiUrl = useApiUrl();
  const { resource, id } = useResourceParams();

  const confirmProcess = () => {
    const { isLoading: prcIsLdn } = useCustom({
      url: `${apiUrl}/${resource}/${id}/process`,
      method: "post",
    });
    setProcessIsLoading(prcIsLdn);
  };

  const handleOpenProcessChange = (newOpen: boolean) => {
    if (!newOpen) {
      setProcessNeedConfirm(newOpen);
    }

    if (processNeedConfirm) {
      confirmProcess();
    } else {
      setProcessNeedConfirm(false);
    }
  };

  useEffect(() => {
    if (!isLoading && data?.data) {
      const st = data.data;

      if (st.status === "OPEN") {
        setStockTakingOpen(true);
      }

      setHeaderInfo([
        {
          key: "1",
          label: "Data de início",
          children: new Date(st.startDate).toLocaleString(),
        },
        {
          key: "2",
          label: "Data final",
          children: st.endDate ? new Date(st.endDate).toLocaleString() : null,
        },
        {
          key: "3",
          label: "Criado por",
          children: st.createdBy.name,
        },
        {
          key: "4",
          label: "Status",
          children: getStatusTag(st.status),
        },
      ]);

      setItems(data.data.items);
    }
  }, [data, isLoading]);

  return (
    <Edit
      saveButtonProps={saveButtonPropsEdit}
      isLoading={isLoading || pdIsLoading || whIsLoading || processIsLoading}
      title="Editar inventário"
    >
      <Form {...formPropsEdit} layout="vertical">
        <Descriptions
          bordered
          column={4}
          items={headerInfo}
          layout="vertical"
          style={{ marginBottom: 30 }}
        />

        <Form.Item
          label="Armazém"
          name="warehouseId"
          rules={[{ required: true, message: "Armazém é obrigatório" }]}
        >
          <Select {...warehouseSelectProps} allowClear />
        </Form.Item>

        <Form.Item label="Observação" name="observation">
          <TextArea rows={3} />
        </Form.Item>

        <Popconfirm
          title="Processar inventário"
          description="Há itens não contados. Deseja processar parcialmente?"
          open={processNeedConfirm}
          onCancel={cancelProcess}
          onConfirm={confirmProcess}
          onOpenChange={handleOpenProcessChange}
          okText="Sim"
          cancelText="Não"
        >
          <Button type="primary" onClick={validateItemsFilled}>
            Processar
          </Button>
        </Popconfirm>

        <Divider orientation="left">Itens</Divider>

        <Form.List name="items">
          {(fields, { add, remove }) => (
            <>
              {fields.map(({ key, name, ...restField }) => {
                const block = rowIsProcessed(name);
                const { hasError, message } = rowHasError(name);
                return (
                  <Space key={key} style={{ display: "flex", marginBottom: 8 }} align="baseline">
                    <Form.Item
                      {...restField}
                      name={[name, "productId"]}
                      rules={[{ required: true, message: "Produto é obrigatório" }]}
                    >
                      <Select
                        {...productSelectProps}
                        placeholder="Selecionar produto"
                        allowClear
                        disabled={block}
                      />
                    </Form.Item>
                    <Form.Item {...restField} name={[name, "systemQuantity"]}>
                      <InputNumber readOnly placeholder="Atual" />
                    </Form.Item>
                    <Form.Item {...restField} name={[name, "countedQuantity"]}>
                      <InputNumber disabled={block} placeholder="Contagem" />
                    </Form.Item>
                    <MinusCircleOutlined disabled={block} onClick={() => remove(name)} />
                    {hasError && (
                      <Popover trigger="hover" title="Erro" content={message}>
                        <CiWarning color="error" />
                      </Popover>
                    )}
                  </Space>
                );
              })}
              <Form.Item>
                {/* TODO: do not let add duplicate entries */}
                <Button
                  hidden={!stockTakingOpen}
                  type="dashed"
                  onClick={() => add()}
                  block
                  icon={<PlusOutlined />}
                >
                  Adicionar item
                </Button>
              </Form.Item>
            </>
          )}
        </Form.List>
      </Form>
    </Edit>
  );
};

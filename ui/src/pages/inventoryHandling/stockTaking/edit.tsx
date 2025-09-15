import { MinusCircleOutlined, PlusOutlined } from "@ant-design/icons";
import { Edit, useForm, useEditableTable, SaveButton, useSelect } from "@refinedev/antd";
import { useMany } from "@refinedev/core";
import { Form, Input, DatePicker, Table, Space, Select, Button, InputNumber, Divider, Descriptions, Tag } from "antd";
import React, { useEffect, useState } from "react";

const { TextArea } = Input;

export const StockTakingEdit = () => {
  const [items, setItems] = useState([]);
  const [headerInfo, setHeaderInfo] = useState<any[]>([]);
  const [userData, setUserData] = useState();

  const { formProps: formPropsEdit, saveButtonProps: saveButtonPropsEdit, query } = useForm();
  const { data, isLoading } = query;

  const getStatusTag = (status: string) => {
    let color;
    let text;

    switch (status) {
      case "OPEN":
        color = "orange";
        text = "Criado";
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

  useEffect(() => {
    if (!isLoading && data?.data) {
      const st = data.data;

      setHeaderInfo([
        {
          key: "1",
          label: "Data de início",
          children: new Date(st.startDate).toLocaleString()
        },
        {
          key: "2",
          label: "Data final",
          children: st.endDate ? new Date(st.endDate).toLocaleString() : null
        },
        {
          key: "3",
          label: "Criado por",
          children: st.createdBy.name
        },
        {
          key: "4",
          label: "Status",
          children: getStatusTag(st.status)
        }
      ])

      setItems(data.data.items);
    }
  }, [data, isLoading]);

  return (
    <Edit saveButtonProps={saveButtonPropsEdit} isLoading={isLoading || pdIsLoading || whIsLoading} title="Editar inventário">
      <Form {...formPropsEdit} layout="vertical">

        {/* <Space align="center" style={{ marginBottom: 20, width: "100%", justifyContent: "flex-end" }}>
        </Space> */}

        <Descriptions bordered column={4} items={headerInfo} layout="vertical" style={{ marginBottom: 30 }} />

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

        <Button type="primary">Processar</Button>
        
        <Divider orientation="left">Itens</Divider>

        <Form.List name="items">
          {(fields, { add, remove }, { errors }) => (
            <>
              {fields.map(({ key, name, ...restField }) => (
                <Space key={key} style={{ display: 'flex', marginBottom: 8 }} align="baseline">

                  <Form.Item
                    {...restField}
                    name={[name, "productId"]}
                    rules={[{ required: true, message: "Produto é obrigatório" }]}
                  >
                    <Select {...productSelectProps} placeholder="Selecionar produto" allowClear />
                  </Form.Item>
                  <Form.Item
                    {...restField}
                    name={[name, 'systemQuantity']}
                  >
                    <InputNumber readOnly placeholder="Atual" />
                  </Form.Item>
                  <Form.Item
                    {...restField}
                    name={[name, 'countedQuantity']}
                  >
                    <InputNumber
                      placeholder="Contagem"
                    />
                  </Form.Item>
                  <MinusCircleOutlined onClick={() => remove(name)} />
                </Space>
              ))}
              <Form.Item>
                <Button type="dashed" onClick={() => add()} block icon={<PlusOutlined />}>
                  Adicionar item
                </Button>
                <Form.ErrorList errors={errors} />
              </Form.Item>
            </>
          )}
        </Form.List>
      </Form>
    </Edit>
  );
};

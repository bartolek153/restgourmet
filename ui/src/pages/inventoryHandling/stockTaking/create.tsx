import { Modal, Form, ModalProps, FormProps, Select, DatePicker, Button, Space, InputNumber, Table, Col, Row, Input } from "antd";
import { useSelect } from "@refinedev/antd";
import { useState } from "react";
import { MinusCircleOutlined, PlusOutlined } from "@ant-design/icons";
import { PaymentMethod } from "../../../types/sales";
import dayjs from "dayjs";

const { TextArea } = Input;

interface SalesOrderFormProps {
  modalProps: ModalProps;
  formProps: FormProps;
}

export const StockTakingCreateForm: React.FC<SalesOrderFormProps> = ({
  modalProps,
  formProps,
}) => {
  const [selectedItems, setSelectedItems] = useState<any[]>([]);

  const { selectProps: warehouseSelectProps } = useSelect({
    resource: "warehouses",
    optionLabel: "name",
    optionValue: "id",
    pagination: {
      mode: "server",
    },
  });

  const { selectProps: productSelectProps } = useSelect({
    resource: "products",
    optionLabel: "description",
    optionValue: "id",
    pagination: {
      mode: "server",
    },
  });

  return (
    <Modal {...modalProps} width={800} title="Criar inventário">
      <Form {...formProps} layout="vertical" >
        <DatePicker defaultValue={dayjs(new Date())} format="DD/MM/YYYY" disabled style={{ marginBottom: 24 }} />
        <Form.Item
          label="Armazém"
          name="warehouseId"
          rules={[
            {
              required: true,
              message: "Armazém é obrigatório",
            },
          ]}
        >
          <Select
            {...warehouseSelectProps}
            placeholder="Selecionar armazém"
            allowClear
            showSearch
            optionFilterProp="label"
          />
        </Form.Item>

        <Form.Item label="Observação" name={"observation"}>
          <TextArea rows={4} />
        </Form.Item>

        <Form.List name="items">
          {(fields, { add, remove }) => (
            <>
              {fields.map(({ key, name, ...restField }) => (
                <Row>
                  <Col flex="auto">
                    <Form.Item
                      {...restField}
                      name={[name, "productId"]}
                      rules={[{ required: true, message: "Produto é obrigatório" }]}
                    >
                      <Select
                        {...productSelectProps}
                        placeholder="Selecionar produto"
                      />
                    </Form.Item>
                  </Col>
                  <Col>
                    <MinusCircleOutlined onClick={() => remove(name)} style={{ fontSize: 18 }} />
                  </Col>
                </Row>
              ))}
              <Form.Item>
                <Button type="dashed" onClick={() => add()} block icon={<PlusOutlined />}>
                  Adicionar produto
                </Button>
              </Form.Item>
            </>
          )}
        </Form.List>
      </Form>
    </Modal>
  );
};

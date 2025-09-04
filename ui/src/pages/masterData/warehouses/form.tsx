import { useSelect } from "@refinedev/antd";
import { Modal, Form, Input, Select, ModalProps, FormProps } from "antd";

interface WarehouseFormProps {
  modalProps: ModalProps;
  formProps: FormProps;
}

export const WarehouseForm: React.FC<WarehouseFormProps> = ({
  modalProps: createModalProps,
  formProps: createFormProps,
}) => {
  const { selectProps } = useSelect({
    resource: "addresses",
    optionLabel: "street",
  });

  return (
    <Modal {...createModalProps} title="Criar armazém">
      <Form {...createFormProps} layout="vertical">
        <Form.Item
          label="Nome"
          name="name"
          rules={[
            {
              required: true,
            },
          ]}
        >
          <Input />
        </Form.Item>
        <Form.Item label="Endereço" name="addressId">
          <Select {...selectProps} />
        </Form.Item>
      </Form>
    </Modal>
  );
};

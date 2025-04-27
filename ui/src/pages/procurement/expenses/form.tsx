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
    <Modal {...createModalProps}>
      <Form {...createFormProps} layout="vertical">
        <Form.Item
          label="Name"
          name="name"
          rules={[
            {
              required: true,
            },
          ]}
        >
          <Input />
        </Form.Item>
        <Form.Item label="Address" name="addressId">
          <Select {...selectProps} />
        </Form.Item>
      </Form>
    </Modal>
  );
};

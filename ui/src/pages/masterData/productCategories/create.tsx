import { Modal, Form, Input, Select, ModalProps, FormProps } from "antd";

interface ProductCategoryFormProps {
  modalProps: ModalProps;
  formProps: FormProps;
}

export const ProductCategoryForm: React.FC<ProductCategoryFormProps> = ({
  modalProps: createModalProps,
  formProps: createFormProps,
}) => {
  return (
    <Modal {...createModalProps}>
      <Form {...createFormProps} layout="vertical">
        <Form.Item
          label="Description"
          name="description"
          rules={[
            {
              required: true,
            },
          ]}
        >
          <Input />
        </Form.Item>
      </Form>
    </Modal>
  );
};

import { useSelect } from "@refinedev/antd";
import { Modal, Form, Input, Select, ModalProps, FormProps } from "antd";

interface ProductCategoryFormProps {
  modalProps: ModalProps;
  formProps: FormProps;
}

export const ProductFamilyForm: React.FC<ProductCategoryFormProps> = ({
  modalProps: createModalProps,
  formProps: createFormProps,
}) => {
  const { selectProps } = useSelect({
    resource: "products/categories",
    optionLabel: "description",
  })
  
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
        <Form.Item
          label="Category"
          name="categoryId"
        >
          <Select {...selectProps} />
        </Form.Item>
      </Form>
    </Modal>
  );
};

import { Create, useForm } from "@refinedev/antd";
import { Form, Input } from "antd";

export const ProductCreate = () => {
  const { formProps, saveButtonProps, onFinish } = useForm({});

  return (
    <Create saveButtonProps={saveButtonProps}>
      <Form {...formProps} layout="vertical" wrapperCol={{ span: 6 }} autoComplete="off">
        <Form.Item label="Description" name="description" rules={[{ required: true }]}>
          <Input />
        </Form.Item>

        <Form.Item label="SKU" name="sku">
          <Input />
        </Form.Item>

        <Form.Item label="Price" name="price" rules={[{ required: true }]}>
          <Input type="number" />
        </Form.Item>
      </Form>
    </Create>
  );
};

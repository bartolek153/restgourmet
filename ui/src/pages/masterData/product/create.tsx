import { Create, useForm, useSelect } from "@refinedev/antd";
import { Form, Input, Select } from "antd";

export const ProductCreate = () => {
  const { formProps, saveButtonProps } = useForm({});

  const { selectProps: invUnSelectProps } = useSelect({
    resource: "units/measurement",
    optionLabel: (item: any) => item.shortDescription || item.description,
  });

  const { selectProps: groupSelectProps } = useSelect({
    resource: "products/groups",
    optionLabel: (item: any) => item.description,
  });

  return (
    <Create saveButtonProps={saveButtonProps}>
      <Form {...formProps} layout="vertical" wrapperCol={{ span: 6 }} autoComplete="off">
        <Form.Item label="Description" name="description" rules={[{ required: true }]}>
          <Input />
        </Form.Item>

        <Form.Item label="SKU" name="sku">
          <Input />
        </Form.Item>

        <Form.Item label="Price" name="price">
          <Input type="number" />
        </Form.Item>

        <Form.Item label="Origin" name="origin" rules={[{ required: true }]}>
          <Select>
            <Select.Option value="0">Produced</Select.Option>
            <Select.Option value="1">Supplied</Select.Option>
            <Select.Option value="2">Imported</Select.Option>
            <Select.Option value="3">Other</Select.Option>
          </Select>
        </Form.Item>

        <Form.Item label="Inventory Unit" name="inventoryUnitId" rules={[{ required: true }]}>
          <Select {...invUnSelectProps} allowClear />
        </Form.Item>

        <Form.Item label="Purchase Unit" name="purchaseUnitId">
          <Select {...invUnSelectProps} allowClear />
        </Form.Item>

        <Form.Item label="Group" name="groupId">
          <Select {...groupSelectProps} allowClear />
        </Form.Item>
      </Form>
    </Create>
  );
};

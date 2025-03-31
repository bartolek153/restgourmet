import { Edit, useForm, useSelect } from "@refinedev/antd";
import { useOne } from "@refinedev/core";
import {
  Form,
  Input,
  Select,
} from "antd";

export const ProductEdit = () => {
  const { formProps, saveButtonProps, query } = useForm({});
  const { data, isLoading } = query;

  const { selectProps: invUnSelectProps } = useSelect({
    resource: "units/measurement",
    optionLabel: (item: any) => item.shortDescription || item.description,
  });

  const { selectProps: groupSelectProps } = useSelect({
    resource: "products/groups",
    optionLabel: (item: any) => item.description,
  });

  const {invUn} = useOne({
    resource: "units/measurement",
    id: data?.inventoryUnitId,
  })

  const {invUn2} = useOne({
    resource: "units/measurement",
    id: data?.purchaseUnitId,
  })

  const {group} = useOne({
    resource: "products/groups",
    id: data?.groupId,
  })

  return (
    <Edit saveButtonProps={saveButtonProps}>
      <Form {...formProps} layout="vertical" wrapperCol={{ span: 6 }} autoComplete="off">
        <Form.Item label="Description" name="description" rules={[{ required: true }]}>
          <Input />
        </Form.Item>

        <Form.Item label="Status" name="status" rules={[{ required: true }]}>
          <Select>
            <Select.Option value="ACTIVE">Active</Select.Option>
            <Select.Option value="DISCONTINUED">Blocked</Select.Option>
            <Select.Option value="OBSOLETE">Discontinued</Select.Option>
            <Select.Option value="BLOCKED">Obsolete</Select.Option>
          </Select>
        </Form.Item>

        <Form.Item label="SKU" name="sku">
          <Input />
        </Form.Item>

        <Form.Item label="Price" name="price">
          <Input type="number" />
        </Form.Item>

        <Form.Item label="Origin" name="origin" rules={[{ required: true }]}>
          <Select>
            <Select.Option value="PRODUCED">Produced</Select.Option>
            <Select.Option value="SUPPLIED">Supplied</Select.Option>
            <Select.Option value="IMPORTED">Imported</Select.Option>
            <Select.Option value="OTHER">Other</Select.Option>
          </Select>
        </Form.Item>

        <Form.Item label="Inventory Unit" name="inventoryUnitId" rules={[{ required: true }]} initialValue={invUn?.id}>
          <Select {...invUnSelectProps} allowClear />
        </Form.Item>

        <Form.Item label="Purchase Unit" name="purchaseUnitId" initialValue={invUn2?.id}>
          <Select {...invUnSelectProps} allowClear />
        </Form.Item>

        <Form.Item label="Group" name="groupId" initialValue={group?.id}>
          <Select {...groupSelectProps} allowClear />
        </Form.Item>
      </Form>
    </Edit>
  );
};


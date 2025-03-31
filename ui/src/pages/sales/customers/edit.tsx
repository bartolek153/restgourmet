import { Edit, useForm, useSelect } from "@refinedev/antd";
import { Form, Select } from "antd";

export const CustomerEdit = () => {
  const { formProps, saveButtonProps } = useForm({});

  const { selectProps } = useSelect({
    resource: "addresses",
    optionLabel: "name",
  });

  return (
    <Edit saveButtonProps={saveButtonProps}>
      <Form {...formProps} layout="vertical">
        <Form.Item label="Billing Address" name="billingAddressId">
          <Select {...selectProps} />
        </Form.Item>
      </Form>
    </Edit>
  );
};

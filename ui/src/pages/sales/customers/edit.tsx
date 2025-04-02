import { Edit, useForm, useSelect } from "@refinedev/antd";
import { useNavigation } from "@refinedev/core";
import { Form, Select } from "antd";

export const CustomerEdit = () => {
  const { edit } = useNavigation();

  const redirectAfterSave = () => {
    edit("partners", id);
  };

  const { formProps, saveButtonProps, id } = useForm({
    redirect: false,
    onMutationSuccess: redirectAfterSave,
  });

  const { selectProps } = useSelect({
    resource: "addresses",
    optionLabel: "name",
  });

  return (
    <Edit saveButtonProps={saveButtonProps} deleteButtonProps={{ onSuccess: redirectAfterSave }}>
      <Form {...formProps} layout="vertical">
        <Form.Item label="Billing Address" name="billingAddressId">
          <Select {...selectProps} />
        </Form.Item>
      </Form>
    </Edit>
  );
};

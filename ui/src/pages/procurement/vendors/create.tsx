import { Create, useForm, useSelect } from "@refinedev/antd";
import { useBack, useNavigation, useParsed } from "@refinedev/core";
import { Form, Select } from "antd";

export const CustomerCreate = () => {
  const { edit } = useNavigation();

  const { params } = useParsed<{ partnerId?: string }>();
  const partnerId = params?.partnerId;

  const { formProps, saveButtonProps, onFinish } = useForm({
    redirect: false,
    onMutationSuccess: () => {
      edit("partners", partnerId!);
    },
  });

  const { selectProps } = useSelect({
    resource: "addresses",
    optionLabel: "name",
  });

  const handleOnFinish = (values: any) => {
    onFinish({
      ...values,
      partnerId,
    });
  };

  return (
    <Create saveButtonProps={saveButtonProps}>
      <Form {...formProps} layout="vertical" onFinish={handleOnFinish}>
        <Form.Item label="Billing Address" name="billingAddressId">
          <Select {...selectProps} />
        </Form.Item>
      </Form>
    </Create>
  );
};

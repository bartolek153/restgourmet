import { Modal, Form, Input, ModalProps, FormProps, Select } from "antd";
import { useSelect } from "@refinedev/antd";

interface BusinessPartnerFormProps {
  modalProps: ModalProps;
  formProps: FormProps;
}

export const BusinessPartnerCreateForm: React.FC<BusinessPartnerFormProps> = ({
  modalProps,
  formProps,
}) => {
  const { selectProps: addressSelectProps } = useSelect({
    resource: "addresses",
    optionLabel: "street",
    optionValue: "id",
    pagination: {
      mode: "server",
    },
  });

  return (
    <Modal {...modalProps}>
      <Form {...formProps} layout="vertical">
        <Form.Item
          label="Name"
          name="name"
          rules={[
            {
              required: true,
              message: "Name is required",
            },
          ]}
        >
          <Input placeholder="Business Partner Name" />
        </Form.Item>
        
        <Form.Item
          label="Email"
          name="email"
          rules={[
            {
              type: "email",
              message: "Please enter a valid email",
            },
          ]}
        >
          <Input placeholder="Email" />
        </Form.Item>
        
        <Form.Item
          label="Phone"
          name="phone"
        >
          <Input placeholder="Phone Number" />
        </Form.Item>
        
        <Form.Item
          label="Address"
          name="addressId"
        >
          <Select
            {...addressSelectProps}
            placeholder="Select an address"
            allowClear
            showSearch
            optionFilterProp="label"
          />
        </Form.Item>
      </Form>
    </Modal>
  );
};

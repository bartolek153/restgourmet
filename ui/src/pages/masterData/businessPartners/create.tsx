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
            },
          ]}
        >
          <Input placeholder="Email" />
        </Form.Item>

        <Form.Item label="Phone" name="phone">
          <Input placeholder="Phone Number" />
        </Form.Item>

        <Form.Item label="Type" name="type">
          <Select placeholder="Select a type" allowClear>
            <Select.Option value="INDIVIDUAL">Individual</Select.Option>
            <Select.Option value="COMPANY">Company</Select.Option>
          </Select>
        </Form.Item>

        <Form.Item label="TIN Type" name="tinType">
          <Select placeholder="Select a TIN type" allowClear>
            <Select.Option value="CPF">CPF</Select.Option>
            <Select.Option value="CNPJ">CNPJ</Select.Option>
          </Select>
        </Form.Item>

        <Form.Item label="TIN" name="taxIdentificationNumber">
          <Input placeholder="Tax Identification Number" />
        </Form.Item>
        
        <Form.Item label="Website" name="website">
          <Input />
        </Form.Item>
      </Form>
    </Modal>
  );
};

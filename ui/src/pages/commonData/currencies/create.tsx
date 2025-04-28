import { Modal, Form, Input, ModalProps, FormProps, Select } from "antd";
import { useSelect } from "@refinedev/antd";

interface BusinessPartnerFormProps {
  modalProps: ModalProps;
  formProps: FormProps;
}

export const CurrencyCreateForm: React.FC<BusinessPartnerFormProps> = ({
  modalProps,
  formProps,
}) => {
  return (
    <Modal {...modalProps}>
      <Form {...formProps} layout="vertical">
        <Form.Item
          label="Code"
          name="code"
          rules={[
            {
              required: true,
            },
          ]}
        >
          <Input />
        </Form.Item>

        <Form.Item
          label="Description"
          name="description"
        >
          <Input />
        </Form.Item>
      </Form>
    </Modal>
  );
};

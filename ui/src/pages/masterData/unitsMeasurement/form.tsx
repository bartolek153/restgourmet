import { useSelect } from "@refinedev/antd";
import { Modal, Form, Input, Select, ModalProps, FormProps } from "antd";

interface UnitMeasurementFormProps {
  modalProps: ModalProps;
  formProps: FormProps;
}

export const UnitMeasurementForm: React.FC<UnitMeasurementFormProps> = ({
  modalProps: createModalProps,
  formProps: createFormProps,
}) => {
  const { selectProps } = useSelect({
    resource: "units",
    optionLabel: "description",
  });
  
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
          label="Short Description"
          name="shortDescription"
        >
          <Input />
        </Form.Item>
        <Form.Item
          label="Base Unit"
          name="baseUnitId"
        >
          <Select {...selectProps} />
        </Form.Item>
        <Form.Item
          label="Conversion Factor"
          name="conversionFactor"
          rules={[
            {
              required: true,
            },
          ]}
        >
          <Input type="number" />
        </Form.Item>
      </Form>
    </Modal>
  );
};

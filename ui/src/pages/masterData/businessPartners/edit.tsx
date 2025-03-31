import { CreateButton, DeleteButton, Edit, EditButton, useForm, useSelect } from "@refinedev/antd";
import { Card, Divider, Form, Input, Select, Space } from "antd";
import { FaCheckSquare, FaTrashAlt } from "react-icons/fa";
import { RefineButtonClassNames } from "@refinedev/ui-types";
import { useEffect, useState } from "react";

export const BusinessPartnerEdit = () => {
  const [isCustomer, setIsCustomer] = useState<Boolean>(false);
  const { formProps, saveButtonProps, query, id } = useForm({});

  const { data, isLoading } = query;

  useEffect(() => {
    if (!isLoading && data?.data) {
      setIsCustomer(data.data.isCustomer);
    }
  }, [data]);

  return (
    <Edit saveButtonProps={saveButtonProps}>
      <Form {...formProps} layout="vertical">
        <Card>
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
          <Form.Item label="Status" name="status">
            <Select>
              <Select.Option value="ACTIVE">Active</Select.Option>
              <Select.Option value="INACTIVE">Inactive</Select.Option>
            </Select>
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
        </Card>

        <Divider />

        <Card>
          <Space direction="horizontal" style={{ width: "100%" }}>
            {!isCustomer ? (
              <CreateButton
                resource="partners/customers"
                type="default"
                meta={{ partnerId: id }}
              >
                Customer
              </CreateButton>
            ) : (
              <EditButton
                resource="partners/customers"
                icon={<FaCheckSquare />}
                type="primary"
                meta={{ partnerId: id }}
              >
                Customer
              </EditButton>
            )}
          </Space>
        </Card>
      </Form>
    </Edit>
  );
};

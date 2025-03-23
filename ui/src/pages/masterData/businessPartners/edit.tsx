import { CreateButton, DeleteButton, Edit, EditButton, useForm, useSelect } from "@refinedev/antd";
import { Card, Divider, Form, Input, Select, Space } from "antd";
import { FaCheckSquare, FaTrashAlt } from "react-icons/fa";
import { RefineButtonClassNames } from "@refinedev/ui-types";
import { useEffect, useState } from "react";

export const BusinessPartnerEdit = () => {
  const [isCustomer, setIsCustomer] = useState<Boolean>(false);
  const { formProps, saveButtonProps, query, id } = useForm({
    redirect: "list",
  });

  const { data, isLoading } = query;

  useEffect(() => {
    if (!isLoading && data?.data) {
      setIsCustomer(data.data.isCustomer);
    }
  }, [data]);

  const { selectProps: addressSelectProps } = useSelect({
    resource: "addresses",
    optionLabel: (item: any) =>
      `${item.street} ${item.number}, ${item.city} - ${item.state}, ${item.country} - ${item.zipCode}`,
    optionValue: (item) => item.id,
    pagination: {
      mode: "server",
    },
  });

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

          <Form.Item label="Phone" name="phone">
            <Input placeholder="Phone Number" />
          </Form.Item>

          <Form.Item label="Address" name="addressId">
            <Select
              {...addressSelectProps}
              placeholder="Select an address"
              allowClear
              showSearch
              optionFilterProp="label"
            />
          </Form.Item>
        </Card>

        <Divider />

        <Card>
          <Space direction="horizontal" style={{ width: "100%" }}>
            {!isCustomer ? (
              <CreateButton
                resource="customers"
                title="Customer"
                type="default"
              >
                Customer
              </CreateButton>
            ) : (
              <EditButton
                resource="customers"
                title="Customer"
                icon={<FaCheckSquare />}
                key={id}
                type="primary"
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

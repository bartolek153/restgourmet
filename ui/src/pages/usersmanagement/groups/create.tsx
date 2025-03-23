import { Create, useForm, useTable } from "@refinedev/antd";
import { Form, Input, Table } from "antd";
import React, { useState } from "react";

export const UserGroupCreate = () => {
  const [permissions, setPermissions] = useState<React.Key[]>([]);
  const { formProps, saveButtonProps, onFinish } = useForm({});

  const handlePermOnChange = (selectedRowKeys: React.Key[], selectedRows: any) => {
    let selectedChildren: React.Key[] = [];

    selectedRows.forEach((row: any) => {
      if (!row.isParent) {
        selectedChildren.push(row.name);
      }
    });

    setPermissions(selectedChildren);
  };

  const handleOnFinish = (values: any) => {
    onFinish({
      ...values,
      permissions,
    });
  };

  const { tableProps } = useTable({
    resource: "authorization/permissions",
    pagination: { mode: "off" },
  });

  return (
    <Create saveButtonProps={saveButtonProps}>
      <Form
        {...formProps}
        onFinish={handleOnFinish}
        layout="vertical"
        wrapperCol={{ span: 6 }}
        autoComplete="off"
      >
        <Form.Item
          label={"Name"}
          name={["name"]}
          rules={[
            {
              required: true,
            },
          ]}
        >
          <Input />
        </Form.Item>
        <Table
          {...tableProps}
          rowKey={"name"}
          rowSelection={{
            // ...permissions,
            selectedRowKeys: permissions,
            onChange: handlePermOnChange,
            checkStrictly: false,
          }}
          rowHoverable={true}
        >
          <Table.Column title="Permissions" dataIndex="name" />
          <Table.Column dataIndex="isParent" hidden />
        </Table>
      </Form>
    </Create>
  );
};

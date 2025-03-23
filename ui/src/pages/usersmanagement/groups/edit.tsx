import { Edit, useForm, useTable } from "@refinedev/antd";
import { Col, Descriptions, Divider, Flex, Form, Grid, Input, Row, Space, Table } from "antd";
import React, { useEffect, useState } from "react";
import { snakeCaseToHumanReadable } from "../../../utils/stringUtils";

export const UserGroupEdit = () => {
  const [permissions, setPermissions] = useState<React.Key[]>([]);
  const [items, setItems] = useState<any[]>([]);
  const { formProps, saveButtonProps, onFinish, query } = useForm({});
  const { data, isLoading } = query;

  useEffect(() => {
    if (!isLoading && data?.data) {
      const role = data.data;
      setPermissions(role.permissions);
      setItems([
        {
          key: "1",
          label: "Created By",
          children: role.createdBy,
        },
        {
          key: "2",
          label: "Created At",
          children: new Date(role.createdAt).toLocaleString(),
        },
        {
          key: "3",
          label: "Updated By",
          children: role.updatedBy,
        },
        {
          key: "4",
          label: "Updated At",
          children: new Date(role.updatedAt).toLocaleString(),
        },
      ]);
    }
  }, [data]);

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
    <Edit saveButtonProps={saveButtonProps} isLoading={isLoading}>
      <Form
        {...formProps}
        onFinish={handleOnFinish}
        layout="vertical"
        wrapperCol={{ span: 6 }}
        autoComplete="off"
      >
        <Space direction="vertical">
          <Form.Item
            label={"Name"}
            name={["name"]}
            style={{ width: "100%" }}
            rules={[
              {
                required: true,
              },
            ]}
          >
            <Input />
          </Form.Item>

          <Descriptions column={4} items={items} layout="vertical" />

          <Divider orientation="left">Permissions</Divider>
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
            showHeader={false}
          >
            <Table.Column
              dataIndex="name"
              render={(text: string, record: any) => {
                return record.isParent ? <strong>{snakeCaseToHumanReadable(text)}</strong> : text;
              }}
            />
            <Table.Column dataIndex="isParent" hidden />
          </Table>
        </Space>
      </Form>
    </Edit>
  );
};

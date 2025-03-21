import { Create, List, useForm, useTable } from "@refinedev/antd";
import { Form, Input, Row, Table } from "antd";
import React, { useEffect, useState } from "react";

export const RoleCreate = () => {
  const [selectedRows, setSelectedRows] = useState<Record<string, React.Key[]>>({});
  const handleSelectionChange = (childKey: string, selectedRowKeys: React.Key[]) => {
    setSelectedRows((prev) => ({
      ...prev,
      [childKey]: selectedRowKeys,
    }));
  };

  const { formProps, saveButtonProps } = useForm({});
  const { tableProps } = useTable({
    resource: "authorization/categories",
    pagination: { mode: "off" },
  });

  useEffect(() => {
    console.log(selectedRows);

  }, [selectedRows]);

  return (
    <Create saveButtonProps={saveButtonProps}>
      <Form {...formProps} layout="vertical" wrapperCol={{ span: 6 }} autoComplete="off">
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
          rowKey={"category"}
          expandable={{
            expandedRowRender: (record: any) => {
              return <PermissionsTable 
                record={record}
                permissions={selectedRows}
                setParentPermissions={handleSelectionChange}
              />;
            },
          }}
        >
          <Table.Column title="Permissions" dataIndex="category" />
        </Table>
      </Form>
    </Create>
  );
};

const PermissionsTable = ({
  record,
  permissions,
  setParentPermissions,
}: {
  record: any;
  permissions: Record<string, React.Key[]>;
  setParentPermissions: (childKey: string, selectedRowKeys: React.Key[]) => void;
}) => {
  const { tableProps: prmTableProps } = useTable({
    resource: "authorization/permissions",
    pagination: { mode: "off" },
    filters: {
      permanent: [
        {
          field: "category",
          operator: "eq",
          value: record.category,
        },
      ],
    },
  });

  const onPermissionsSelectChange = (selectedRowKeys: React.Key[]) => {
    console.log(selectedRowKeys)
    setParentPermissions(record.category, selectedRowKeys);
  };

  return (
    <Table
      showHeader={false}
      {...prmTableProps}
      rowKey={"name"}
      pagination={false}
      rowSelection={{
        selectedRowKeys: permissions[record.category],
        onChange: onPermissionsSelectChange,
        // preserveSelectedRowKeys: true,
      }}
    >
      <Table.Column dataIndex={"name"} />
    </Table>
  );
};

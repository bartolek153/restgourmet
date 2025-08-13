import { DeleteButton, EditButton, List, ShowButton, useTable } from "@refinedev/antd";
import { BaseRecord } from "@refinedev/core";
import { Space, Table } from "antd";

export const RoleList = () => {
  const { tableProps } = useTable({});

  return (
    <List>
      <Table
        {...tableProps}
        rowKey={"id"}
        expandable={{
          expandedRowRender: (record: any) => {
            return <PermissionsTable {...record} />;
          },
        }}
      >
        <Table.Column title={"Nome"} dataIndex={"name"} />
        <Table.Column title={"Criado por"} dataIndex={"createdBy"} />
        <Table.Column
          title={"Atualizado em"}
          dataIndex={"updatedAt"}
          render={(text) => new Date(text).toLocaleString()}
        />
        <Table.Column
          title={"Ações"}
          dataIndex="actions"
          render={(_, record: BaseRecord) => (
            <Space>
              <EditButton hideText size="small" recordItemId={record.id} />
              <DeleteButton hideText size="small" recordItemId={record.id} />
              {/* <ShowButton hideText size="small" recordItemId={record.id} /> */}
            </Space>
          )}
        />
      </Table>
    </List>
  );
};

const PermissionsTable = (record: any) => {
  const { tableProps: prmTableProps } = useTable({
    resource: "users/roles/permissions",
    filters: {
      permanent: [
        {
          field: "id",
          operator: "eq",
          value: record.id,
        },
      ],
    },
  });

  return (
    <Table showHeader={false} {...prmTableProps} rowKey={"id"} pagination={false}>
      <Table.Column dataIndex={"name"} />
    </Table>
  );
};

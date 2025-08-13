import { DeleteButton, EditButton, FilterDropdown, List, useTable } from "@refinedev/antd";
import type { BaseRecord } from "@refinedev/core";
import { Checkbox, Form, Input, Radio, Space, Table } from "antd";
import { AiOutlineSearch } from "react-icons/ai";

export const UserList = () => {
  const { tableProps, searchFormProps } = useTable({
    syncWithLocation: true,
    onSearch: (params: any) => {
      return [{ field: "q", operator: "eq", value: params.q }];
    },
  });

  const filterPageDelayed = () => {
    setTimeout(() => {
      searchFormProps.form?.submit();
    }, 1000);
  };

  return (
    <List>
      <Form {...searchFormProps} layout="horizontal" onValuesChange={filterPageDelayed}>
        <Space wrap>
          <Form.Item label="Pesquisa" name="q">
            <Input placeholder="ID, Nome, Usuário, etc." prefix={<AiOutlineSearch />} allowClear />
          </Form.Item>
        </Space>
      </Form>
      <Table
        {...tableProps}
        rowKey="id"
        pagination={{
          ...tableProps.pagination,
          showTotal: (total) => `${total} registro(s)`,
          showQuickJumper: true,
          showSizeChanger: true,
        }}
        showSorterTooltip={true}
      >
        <Table.Column dataIndex="name" title={"Nome"} sorter={true} />
        <Table.Column dataIndex="email" title={"E-mail"} />
        <Table.Column dataIndex="nickname" title={"Usuário"} />
        <Table.Column dataIndex="type" title={"Tipo"} />
        <Table.Column
          dataIndex="enabled"
          render={(text) => <Checkbox checked={text} />}
          title={"Ativo"}
          filterDropdown={(props) => (
            <FilterDropdown {...props}>
              <Radio.Group>
                <Radio value="true">Ativo</Radio>
                <Radio value="false">Inativo</Radio>
              </Radio.Group>
            </FilterDropdown>
          )}
        />
        <Table.Column
          dataIndex="createdAt"
          title={"Criado em"}
          sorter={true}
          render={(text) => new Date(text).toLocaleString()}
        />
        <Table.Column
          title={"Ações"}
          dataIndex="actions"
          render={(_, record: BaseRecord) => (
            <Space>
              <EditButton hideText size="small" recordItemId={record.id} />
              <DeleteButton hideText size="small" recordItemId={record.id} />
            </Space>
          )}
        />
      </Table>
    </List>
  );
};

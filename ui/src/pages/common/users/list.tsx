import {
  DeleteButton,
  EditButton,
  FilterDropdown,
  List,
  ShowButton,
  useTable,
} from "@refinedev/antd";
import type { BaseRecord } from "@refinedev/core";
import { Button, Card, Checkbox, Col, Form, Input, Radio, Row, Space, Table } from "antd";
import { AiOutlineSearch } from "react-icons/ai";

export const UserList = () => {
  const { tableProps, searchFormProps } = useTable({
    syncWithLocation: true,
    onSearch: (params: any) => {
      return [
        {field: 'q', operator: "eq", value: params.q},
      ]
    }
  });

  const filterPageDelayed = () => {
    setTimeout(() => {
      searchFormProps.form?.submit();
    }, 1000);
  }

  return (
    <List>
      <Form
        {...searchFormProps}
        layout="horizontal"
        onValuesChange={filterPageDelayed}
      >
        <Space wrap>
          <Form.Item label="Search" name="q">
            <Input
              placeholder="ID, Name, Nick, etc."
              prefix={<AiOutlineSearch />}
              allowClear
            />
          </Form.Item>
        </Space>
      </Form>
      <Table {...tableProps} rowKey="id" pagination={{
        ...tableProps.pagination,
        showTotal: (total) => `Total ${total} items`,
        showQuickJumper: true,
        showSizeChanger: true,
      }}
        showSorterTooltip={true}
      >
        <Table.Column dataIndex="name" title={"Name"} sorter={true} />
        <Table.Column dataIndex="email" title={"Email"} />
        <Table.Column dataIndex="nickname" title={"Nickname"} />
        <Table.Column dataIndex="role" title={"Role"} />
        <Table.Column dataIndex="enabled" render={(text) => <Checkbox checked={text} />} title={"Enabled"}
          filterDropdown={(props) => (
            <FilterDropdown {...props}>
              <Radio.Group>
                <Radio value="true">Enabled</Radio>
                <Radio value="false">Disabled</Radio>
              </Radio.Group>
            </FilterDropdown>
          )}
        />
        <Table.Column dataIndex="createdAt" title={"Created at"}
          sorter={true}
          render={(text) => new Date(text).toLocaleString()}
        />
        <Table.Column
          title={"Actions"}
          dataIndex="actions"
          render={(_, record: BaseRecord) => (
            <Space>
              <EditButton hideText size="small" recordItemId={record.id} />
              {/* <ShowButton hideText size="small" recordItemId={record.id} /> */}
              <DeleteButton hideText size="small" recordItemId={record.id} />
            </Space>
          )}
        />
      </Table>
    </List>
  );
};

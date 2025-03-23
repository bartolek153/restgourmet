import {
  DeleteButton,
  EditButton,
  List,
  useModalForm,
  useTable,
} from "@refinedev/antd";
import { BaseRecord } from "@refinedev/core";
import { Form, Input, Space, Table } from "antd";
import { AiOutlineSearch } from "react-icons/ai";
import { BusinessPartnerCreateForm } from "./create";

export const BusinessPartnerList = () => {
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

  const {
    modalProps: createModalProps,
    formProps: createFormProps,
    show: createModalShow,
  } = useModalForm({
    action: "create",
    redirect: "edit",
  });

  return (
    <>
      <List
        createButtonProps={{
          onClick: () => {
            createModalShow();
          },
        }}
      >
        <Form {...searchFormProps} layout="horizontal" onValuesChange={filterPageDelayed}>
          <Space wrap>
            <Form.Item label="Search" name="q">
              <Input placeholder="Name, Email, Phone..." prefix={<AiOutlineSearch />} allowClear />
            </Form.Item>
          </Space>
        </Form>

        <Table
          {...tableProps}
          rowKey="id"
          pagination={{
            ...tableProps.pagination,
            showTotal: (total) => `Total ${total} items`,
            showQuickJumper: true,
            showSizeChanger: true,
          }}
          showSorterTooltip={true}
        >
          <Table.Column dataIndex="name" title={"Name"} sorter={true} />
          <Table.Column dataIndex="email" title={"Email"} sorter={true} />
          <Table.Column dataIndex="phone" title={"Phone"} sorter={true} />
          <Table.Column dataIndex="addressCity" title={"City"} sorter={true} />
          <Table.Column dataIndex="addressState" title={"State"} sorter={true} />
          <Table.Column
            title={"Actions"}
            dataIndex="actions"
            render={(_, record: BaseRecord) => (
              <Space>
                <EditButton
                  hideText
                  size="small"
                  recordItemId={record.id}
                />
                <DeleteButton hideText size="small" recordItemId={record.id} />
              </Space>
            )}
          />
        </Table>
      </List>
      <BusinessPartnerCreateForm modalProps={createModalProps} formProps={createFormProps} />
    </>
  );
};

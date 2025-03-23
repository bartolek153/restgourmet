import {
  DeleteButton,
  EditButton,
  FilterDropdown,
  List,
  useModalForm,
  useTable,
} from "@refinedev/antd";
import { BaseRecord } from "@refinedev/core";
import { Form, Input, Space, Table } from "antd";
import { AiOutlineSearch } from "react-icons/ai";
import { AddressForm } from "./create";

export const AddressList = () => {
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
  });

  const {
    modalProps: editModalProps,
    formProps: editFormProps,
    show: editModalShow,
  } = useModalForm({
    action: "edit",
    warnWhenUnsavedChanges: true,
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
              <Input placeholder="Street, City, Zip Code..." prefix={<AiOutlineSearch />} allowClear />
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
          <Table.Column dataIndex="street" title={"Street"} sorter={true} />
          <Table.Column dataIndex="number" title={"Number"} sorter={true} />
          <Table.Column dataIndex="city" title={"City"} sorter={true} />
          <Table.Column dataIndex="state" title={"State"} sorter={true} />
          <Table.Column dataIndex="zipCode" title={"Zip Code"} sorter={true} />
          <Table.Column dataIndex="country" title={"Country"} sorter={true} />
          <Table.Column
            title={"Actions"}
            dataIndex="actions"
            render={(_, record: BaseRecord) => (
              <Space>
                <EditButton
                  hideText
                  size="small"
                  recordItemId={record.id}
                  onClick={() => editModalShow(record.id)}
                />
                <DeleteButton hideText size="small" recordItemId={record.id} />
              </Space>
            )}
          />
        </Table>
      </List>
      <AddressForm modalProps={createModalProps} formProps={createFormProps} />
      <AddressForm modalProps={editModalProps} formProps={editFormProps} />
    </>
  );
};

import {
  DeleteButton,
  EditButton,
  FilterDropdown,
  List,
  TextField,
  useModalForm,
  useSelect,
  useTable,
} from "@refinedev/antd";
import { BaseRecord, useMany } from "@refinedev/core";
import { Form, Input, Space, Table } from "antd";
import { AiOutlineSearch } from "react-icons/ai";
import { AddressForm } from "./form";
import { StockTakingCreateForm } from "./create";

export const StockTakingList = () => {
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
    resource: "stock/takings",
    action: "create",
    redirect: "edit",
  });

  const {
    modalProps: editModalProps,
    formProps: editFormProps,
    show: editModalShow,
  } = useModalForm({
    action: "edit",
    warnWhenUnsavedChanges: true,
  });

  const { selectProps: userSelectProps } = useSelect({
    resource: "users",
    optionLabel: "name",
  });

  const userIds: any = tableProps?.dataSource?.map((record: any) => record.createdById);
  const { data, isLoading } = useMany({
    resource: "users",
    ids: userIds,
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
            <Form.Item label="Pesquisar" name="q">
              <Input placeholder="" prefix={<AiOutlineSearch />} allowClear />
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
          <Table.Column dataIndex="startDate" title={"Data inicial"} sorter={true} />
          <Table.Column dataIndex="endDate" title={"Data final"} sorter={true} />
          <Table.Column dataIndex="createdById" title={"Criado por"} sorter={true} />
          <Table.Column
                          dataIndex="createdById"
                          sorter={true}
                          title={"Criado por"}
                          render={(value) => {
                            if (isLoading) {
                              return <TextField value="Loading..." />;
                            }
          
                            return <TextField value={data?.find((item) => item.id === value)?.name} />;
                          }}
                        />
          <Table.Column dataIndex="status" title={"Status"} sorter={true} />
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
      <StockTakingCreateForm modalProps={createModalProps} formProps={createFormProps} />
    </>
  );
};

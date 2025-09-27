import {
  DeleteButton,
  EditButton,
  List,
  TextField,
  useModalForm,
  useSelect,
  useTable,
} from "@refinedev/antd";
import { BaseRecord, useMany } from "@refinedev/core";
import { Form, Input, Space, Table, Tag } from "antd";
import { AiOutlineSearch } from "react-icons/ai";
import { StockTakingCreateForm } from "./create";
import { text } from "stream/consumers";

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

  const getStatusTag = (status: string) => {
    let color;
    let text;

    switch (status) {
      case "OPEN":
        color = "orange";
        text = "Criado";
        break;
      case "PARTIALLY_PROCESSED":
        color = "red";
        text = "Parcialmente processado";
        break;
      case "CLOSED":
        color = "green";
        text = "Fechado";
        break;
      case "CANCELED":
        color = "gray";
        text = "Cancelado";
        break;
    }

    return <Tag color={color}>{text}</Tag>;
  };

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
          <Table.Column
            dataIndex="startDate"
            title={"Data inicial"}
            sorter={true}
            render={(text) => new Date(text).toLocaleString()}
          />
          <Table.Column 
            dataIndex="endDate" 
            title={"Data final"} 
            sorter={true} 
            render={(text) => new Date(text).toLocaleString()}
          />
          <Table.Column
            dataIndex={["createdBy", "name"]}
            sorter={true}
            title={"Criado por"}
          />
          <Table.Column
            dataIndex="status"
            title={"Status"}
            sorter={true}
            render={(val) => getStatusTag(val)}
          />
          <Table.Column dataIndex="observation" title="Observação" />
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
      <StockTakingCreateForm modalProps={createModalProps} formProps={createFormProps} />
    </>
  );
};

import { DeleteButton, EditButton, List, useModalForm, useTable } from "@refinedev/antd";
import { BaseRecord } from "@refinedev/core";
import { DatePicker, Form, Input, Space, Table, Tag } from "antd";
import { PurchaseOrderCreateForm } from "./create";
import { OrderStatus } from "../../../types/sales";

const { RangePicker } = DatePicker;

export const PurchaseOrderList = () => {
  const { tableProps, searchFormProps } = useTable({
    resource: "sales/orders",
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
    resource: "purchases/orders",
    action: "create",
    redirect: "edit",
  });

  const getStatusTag = (status: OrderStatus) => {
    const statusColors = {
      PENDING: "orange",
      APPROVED: "green",
      PROCESSING: "blue",
      SHIPPED: "cyan",
      DELIVERED: "green",
      CANCELLED: "red",
    };

    return <Tag color={statusColors[status]}>{status}</Tag>;
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
        <Form {...searchFormProps} layout="vertical" onValuesChange={filterPageDelayed}>
          <Form.Item>
            <Input name="q" />
          </Form.Item>
          <Form.Item label="Order Date" name="orderDateRange">
            <RangePicker />
          </Form.Item>
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
        >
          <Table.Column dataIndex="orderNumber" title={"Order Number"} sorter={true} />
          <Table.Column
            dataIndex="status"
            title={"Status"}
            sorter={true}
            render={(value) => getStatusTag(value)}
          />
          <Table.Column dataIndex="orderDate" title={"Order Date"} sorter={true} />
          <Table.Column
            title={"Actions"}
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
      <PurchaseOrderCreateForm modalProps={createModalProps} formProps={createFormProps} />
    </>
  );
};

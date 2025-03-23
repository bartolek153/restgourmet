import {
  DeleteButton,
  EditButton,
  List,
  useModalForm,
  useTable,
} from "@refinedev/antd";
import { BaseRecord, CrudFilters } from "@refinedev/core";
import { DatePicker, Form, Input, Select, Space, Table, Tag } from "antd";
import { AiOutlineSearch } from "react-icons/ai";
import { SalesOrderCreateForm } from "./create";
import { OrderStatus } from "../../../types/sales";

const { RangePicker } = DatePicker;

export const SalesOrderList = () => {
  const { tableProps, searchFormProps } = useTable({
    resource: "sales/orders",
    syncWithLocation: true,
    onSearch: (params: any) => {
      const filters: CrudFilters = [];
      
      if (params.orderNumber) {
        filters.push({ field: "orderNumber", operator: "contains", value: params.orderNumber });
      }
      
      if (params.customerName) {
        filters.push({ field: "customerName", operator: "contains", value: params.customerName });
      }
      
      if (params.status) {
        filters.push({ field: "status", operator: "eq", value: params.status });
      }
      
      if (params.paymentMethod) {
        filters.push({ field: "paymentMethod", operator: "eq", value: params.paymentMethod });
      }
      
      if (params.orderDateRange && params.orderDateRange[0] && params.orderDateRange[1]) {
        filters.push({ 
          field: "orderDateFrom", 
          operator: "gte", 
          value: params.orderDateRange[0].format("YYYY-MM-DD") 
        });
        filters.push({ 
          field: "orderDateTo", 
          operator: "lte", 
          value: params.orderDateRange[1].format("YYYY-MM-DD") 
        });
      }
      
      if (params.deliveryDateRange && params.deliveryDateRange[0] && params.deliveryDateRange[1]) {
        filters.push({ 
          field: "deliveryDateFrom", 
          operator: "gte", 
          value: params.deliveryDateRange[0].format("YYYY-MM-DD") 
        });
        filters.push({ 
          field: "deliveryDateTo", 
          operator: "lte", 
          value: params.deliveryDateRange[1].format("YYYY-MM-DD") 
        });
      }
      
      return filters;
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
    resource: "sales/orders",
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

    return (
      <Tag color={statusColors[status]}>
        {status}
      </Tag>
    );
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
          <Space wrap>
            <Form.Item label="Order Number" name="orderNumber">
              <Input placeholder="Order Number" prefix={<AiOutlineSearch />} allowClear />
            </Form.Item>
            <Form.Item label="Customer" name="customerName">
              <Input placeholder="Customer Name" prefix={<AiOutlineSearch />} allowClear />
            </Form.Item>
            <Form.Item label="Status" name="status">
              <Select
                placeholder="Select Status"
                allowClear
                options={[
                  { label: "Pending", value: "PENDING" },
                  { label: "Approved", value: "APPROVED" },
                  { label: "Processing", value: "PROCESSING" },
                  { label: "Shipped", value: "SHIPPED" },
                  { label: "Delivered", value: "DELIVERED" },
                  { label: "Cancelled", value: "CANCELLED" },
                ]}
              />
            </Form.Item>
            <Form.Item label="Payment Method" name="paymentMethod">
              <Select
                placeholder="Select Payment Method"
                allowClear
                options={[
                  { label: "Credit Card", value: "CREDIT_CARD" },
                  { label: "Debit Card", value: "DEBIT_CARD" },
                  { label: "Bank Transfer", value: "BANK_TRANSFER" },
                  { label: "Cash", value: "CASH" },
                  { label: "Check", value: "CHECK" },
                  { label: "PayPal", value: "PAYPAL" },
                ]}
              />
            </Form.Item>
            <Form.Item label="Order Date" name="orderDateRange">
              <RangePicker />
            </Form.Item>
            <Form.Item label="Delivery Date" name="deliveryDateRange">
              <RangePicker />
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
          <Table.Column dataIndex="orderNumber" title={"Order Number"} sorter={true} />
          <Table.Column dataIndex="customerName" title={"Customer"} sorter={true} />
          <Table.Column 
            dataIndex="status" 
            title={"Status"} 
            sorter={true}
            render={(value) => getStatusTag(value)}
          />
          <Table.Column dataIndex="paymentMethod" title={"Payment Method"} sorter={true} />
          <Table.Column dataIndex="orderDate" title={"Order Date"} sorter={true} />
          <Table.Column dataIndex="deliveryDate" title={"Delivery Date"} sorter={true} />
          <Table.Column 
            dataIndex="totalAmount" 
            title={"Total Amount"} 
            sorter={true}
            render={(value) => `$${value.toFixed(2)}`}
          />
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
      <SalesOrderCreateForm modalProps={createModalProps} formProps={createFormProps} />
    </>
  );
};

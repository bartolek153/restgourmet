import { Edit, useForm, useSelect } from "@refinedev/antd";
import { Card, DatePicker, Form, InputNumber, Select, Button, Space, Divider, Tag } from "antd";
import { MinusCircleOutlined, PlusOutlined } from "@ant-design/icons";
import { useEffect, useState } from "react";
import { OrderStatus, PaymentMethod } from "../../../types/sales";
import dayjs from "dayjs";

export const SalesOrderEdit = () => {
  const { formProps, saveButtonProps, queryResult } = useForm({
    resource: "sales/orders",
    redirect: "list",
  });

  const orderData = queryResult?.data?.data;

  const { selectProps: customerSelectProps } = useSelect({
    resource: "customers",
    optionLabel: "name",
    optionValue: "id",
    pagination: {
      mode: "server",
    },
  });

  const { selectProps: addressSelectProps } = useSelect({
    resource: "addresses",
    optionLabel: "street",
    optionValue: "id",
    pagination: {
      mode: "server",
    },
  });

  const { selectProps: warehouseSelectProps } = useSelect({
    resource: "warehouses",
    optionLabel: "name",
    optionValue: "id",
    pagination: {
      mode: "server",
    },
  });

  const { selectProps: productSelectProps } = useSelect({
    resource: "products",
    optionLabel: "name",
    optionValue: "id",
    pagination: {
      mode: "server",
    },
  });

  const getStatusTag = (status: OrderStatus) => {
    const statusColors = {
      [OrderStatus.PENDING]: "orange",
      [OrderStatus.APPROVED]: "green",
      [OrderStatus.PROCESSING]: "blue",
      [OrderStatus.SHIPPED]: "cyan",
      [OrderStatus.DELIVERED]: "green",
      [OrderStatus.CANCELLED]: "red",
    };

    return (
      <Tag color={statusColors[status]}>
        {status}
      </Tag>
    );
  };

  return (
    <Edit saveButtonProps={saveButtonProps}>
      <Form {...formProps} layout="vertical">
        <Card title="Order Information">
          {orderData?.orderNumber && (
            <div style={{ marginBottom: 16 }}>
              <strong>Order Number:</strong> {orderData.orderNumber}
            </div>
          )}
          
          {orderData?.status && (
            <div style={{ marginBottom: 16 }}>
              <strong>Status:</strong> {getStatusTag(orderData.status)}
            </div>
          )}
          
          {orderData?.orderDate && (
            <div style={{ marginBottom: 16 }}>
              <strong>Order Date:</strong> {orderData.orderDate}
            </div>
          )}
          
          <Form.Item
            label="Customer"
            name="customerId"
            rules={[
              {
                required: true,
                message: "Customer is required",
              },
            ]}
          >
            <Select
              {...customerSelectProps}
              placeholder="Select a customer"
              allowClear
              showSearch
              optionFilterProp="label"
            />
          </Form.Item>
          
          <Form.Item
            label="Delivery Address"
            name="deliveryAddressId"
            rules={[
              {
                required: true,
                message: "Delivery address is required",
              },
            ]}
          >
            <Select
              {...addressSelectProps}
              placeholder="Select a delivery address"
              allowClear
              showSearch
              optionFilterProp="label"
            />
          </Form.Item>
          
          <Form.Item
            label="Delivery Date"
            name="deliveryDate"
            rules={[
              {
                required: true,
                message: "Delivery date is required",
              },
            ]}
            getValueProps={(value) => ({
              value: value ? dayjs(value) : undefined,
            })}
          >
            <DatePicker style={{ width: '100%' }} />
          </Form.Item>
          
          <Form.Item
            label="Payment Method"
            name="paymentMethod"
            rules={[
              {
                required: true,
                message: "Payment method is required",
              },
            ]}
          >
            <Select
              placeholder="Select payment method"
              allowClear
              options={[
                { label: "Credit Card", value: PaymentMethod.CREDIT_CARD },
                { label: "Debit Card", value: PaymentMethod.DEBIT_CARD },
                { label: "Bank Transfer", value: PaymentMethod.BANK_TRANSFER },
                { label: "Cash", value: PaymentMethod.CASH },
                { label: "Check", value: PaymentMethod.CHECK },
                { label: "PayPal", value: PaymentMethod.PAYPAL },
              ]}
            />
          </Form.Item>
          
          <Form.Item
            label="Warehouse"
            name="warehouseId"
            rules={[
              {
                required: true,
                message: "Warehouse is required",
              },
            ]}
          >
            <Select
              {...warehouseSelectProps}
              placeholder="Select a warehouse"
              allowClear
              showSearch
              optionFilterProp="label"
            />
          </Form.Item>
        </Card>
        
        <Divider />
        
        <Card title="Order Items">
          <Form.List name="items">
            {(fields, { add, remove }) => (
              <>
                {fields.map(({ key, name, ...restField }) => (
                  <Space key={key} style={{ display: 'flex', marginBottom: 8 }} align="baseline">
                    <Form.Item
                      {...restField}
                      name={[name, 'productId']}
                      rules={[{ required: true, message: 'Product is required' }]}
                    >
                      <Select
                        {...productSelectProps}
                        placeholder="Select product"
                        style={{ width: 200 }}
                      />
                    </Form.Item>
                    <Form.Item
                      {...restField}
                      name={[name, 'quantity']}
                      rules={[{ required: true, message: 'Quantity is required' }]}
                    >
                      <InputNumber min={1} placeholder="Quantity" />
                    </Form.Item>
                    <Form.Item
                      {...restField}
                      name={[name, 'unitPrice']}
                      rules={[{ required: true, message: 'Unit price is required' }]}
                    >
                      <InputNumber
                        min={0}
                        step={0.01}
                        precision={2}
                        placeholder="Unit Price"
                        formatter={(value) => (value ? `$ ${value}` : '')}
                        parser={(value) => (value ? value.replace(/\$\s?|(,*)/g, '') : '')}
                      />
                    </Form.Item>
                    <Form.Item
                      {...restField}
                      name={[name, 'taxRate']}
                      rules={[{ required: true, message: 'Tax rate is required' }]}
                    >
                      <InputNumber
                        min={0}
                        max={100}
                        step={0.1}
                        precision={1}
                        placeholder="Tax Rate"
                        formatter={(value) => (value ? `${value}%` : '')}
                        parser={(value) => (value ? value.replace('%', '') : '')}
                      />
                    </Form.Item>
                    <MinusCircleOutlined onClick={() => remove(name)} />
                  </Space>
                ))}
                <Form.Item>
                  <Button type="dashed" onClick={() => add()} block icon={<PlusOutlined />}>
                    Add Item
                  </Button>
                </Form.Item>
              </>
            )}
          </Form.List>
        </Card>
        
        {orderData?.status && orderData.status !== OrderStatus.CANCELLED && (
          <Card title="Order Status" style={{ marginTop: 16 }}>
            <Space>
              {orderData.status === OrderStatus.PENDING && (
                <Button type="primary" onClick={() => {
                  // Call API to update status
                  // salesOrderService.updateStatus(orderData.id, OrderStatus.APPROVED)
                }}>
                  Approve Order
                </Button>
              )}
              
              {orderData.status === OrderStatus.APPROVED && (
                <Button type="primary" onClick={() => {
                  // Call API to update status
                  // salesOrderService.updateStatus(orderData.id, OrderStatus.PROCESSING)
                }}>
                  Start Processing
                </Button>
              )}
              
              {orderData.status === OrderStatus.PROCESSING && (
                <Button type="primary" onClick={() => {
                  // Call API to update status
                  // salesOrderService.updateStatus(orderData.id, OrderStatus.SHIPPED)
                }}>
                  Mark as Shipped
                </Button>
              )}
              
              {orderData.status === OrderStatus.SHIPPED && (
                <Button type="primary" onClick={() => {
                  // Call API to update status
                  // salesOrderService.updateStatus(orderData.id, OrderStatus.DELIVERED)
                }}>
                  Mark as Delivered
                </Button>
              )}
              
              {orderData.status !== OrderStatus.DELIVERED && (
                <Button danger onClick={() => {
                  // Call API to update status
                  // salesOrderService.updateStatus(orderData.id, OrderStatus.CANCELLED)
                }}>
                  Cancel Order
                </Button>
              )}
            </Space>
          </Card>
        )}
      </Form>
    </Edit>
  );
};

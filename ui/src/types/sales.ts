export enum OrderStatus {
  PENDING = "PENDING",
  APPROVED = "APPROVED",
  PROCESSING = "PROCESSING",
  SHIPPED = "SHIPPED",
  DELIVERED = "DELIVERED",
  CANCELLED = "CANCELLED"
}

export enum PaymentMethod {
  CREDIT_CARD = "CREDIT_CARD",
  DEBIT_CARD = "DEBIT_CARD",
  BANK_TRANSFER = "BANK_TRANSFER",
  CASH = "CASH",
  CHECK = "CHECK",
  PAYPAL = "PAYPAL"
}

export interface SalesOrderItemDto {
  productId: string;
  quantity: number;
  unitPrice: number;
  taxRate: number;
}

export interface SalesOrderDto {
  customerId: string;
  deliveryAddressId: string;
  deliveryDate: string;
  paymentMethod: PaymentMethod;
  warehouseId: string;
  items: SalesOrderItemDto[];
}

export interface SalesOrderListDto {
  id: string;
  orderNumber: string;
  customerName: string;
  status: OrderStatus;
  paymentMethod: PaymentMethod;
  orderDate: string;
  deliveryDate: string;
  totalAmount: number;
}

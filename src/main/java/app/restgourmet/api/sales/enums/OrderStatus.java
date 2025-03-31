package app.restgourmet.api.sales.enums;

public enum OrderStatus {

  /**
   * The order has been created but is waiting for confirmation or payment.
   * No processing has started yet.
   */
  PENDING,

  /**
   * The order has been approved for processing.
   * Payment may have been confirmed, and it is ready for fulfillment.
   */
  APPROVED,

  /**
   * The order is currently being prepared.
   * This includes picking, packing, or manufacturing (if applicable).
   */
  PROCESSING,

  /**
   * The order has been shipped to the customer.
   * It is now in transit and can often be tracked.
   */
  SHIPPED,

  /**
   * The order has been successfully delivered to the customer.
   * This is the final status for completed orders.
   */
  DELIVERED,

  /**
   * The order has been canceled and will not be processed further.
   * This could be due to customer request, payment failure, or stock issues.
   */
  CANCELLED
}

package app.restgourmet.api.utils;

public final class AppConstants {

  public static class CacheKeys {
    public static final String USER_AUTHENTICATION = "users_auth";
  }

  public static class Codes {
    public static final String BRAZIL_COUNTRY_CODE = "BR";
  }

  public static class ErrorMessages {
    public static final String BASE_UNIT_NOT_FOUND = "Base unit not found";

    public static final String ADDRESS_NOT_FOUND = "Address not found";
    public static final String ADDRESS_DELETE_DEPS = "Cannot delete address as it has dependent entities";

    public static final String BUSINESS_PARTNER_NOT_FOUND = "Business partner not found";
    public static final String BUSINESS_PARTNER_DELETE_DEPS = "Cannot delete business partner as it has dependent entities";

    public static final String CEP_INVALID_FORMAT = "Invalid CEP format";

    public static final String CURRENCY_NOT_FOUND = "Currency not found";
    public static final String CURRENCY_ALREADY_EXISTS = "The currency code is already registered";

    public static final String CUSTOMER_ALREADY_EXISTS = "Customer already exists";
    public static final String CUSTOMER_NOT_FOUND = "Customer not found";

    public static final String EXCHANGE_RATE_UNAVAILABLE_SERVICE = "Could not obtain the exchange rate using a third pary service. Try again later or fill the form manually.";

    public static final String EXPENSE_NOT_FOUND = "Expense not found";

    public static final String EXPENSE_CATEGORY_NOT_FOUND = "Expense category not found";

    public static final String EXTERNAL_SERVICE_ERROR = "External service error";
    public static final String EXTERNAL_DATABASE_ERROR = "A database error occurred while processing the request. Please, try again later.";

    public static final String STOCK_INSUFFICIENT = "Insufficient inventory available to fulfill the request.";
    public static final String STOCK_NOT_FOUND = "Inventory record not found.";
    public static final String STOCK_QUANTITY_EXCEEDED = "Requested quantity exceeds available inventory.";
    public static final String STOCK_UNAVAILABLE = "Inventory is currently unavailable.";
    
    public static final String PARAMETER_NOT_FOUND = "Parameter not found.";

    public static final String PAYMENT_TERM_NOT_FOUND = "Payment term not found";
    
    public static final String PERMISSION_NOT_FOUND = "Permission not found";
    
    public static final String PRODUCT_NOT_FOUND = "Product not found";
    public static final String PRODUCT_DELETE_DEPS = "Cannot delete product as it has dependent entities";
    public static final String PRODUCT_SKU_EXISTS = "Product with this SKU already exists";
    
    public static final String PRODUCT_CATEGORY_NOT_FOUND = "Category not found";
    public static final String PRODUCT_CATEGORY_DELETE_DEPS = "Cannot delete category as it has dependent family";
    
    public static final String PRODUCT_FAMILY_NOT_FOUND = "Family not found";
    public static final String PRODUCT_FAMILY_DELETE_DEPS = "Cannot delete family as it has dependent group";
    
    public static final String PRODUCT_GROUP_NOT_FOUND = "Group not found";
    public static final String PRODUCT_GROUP_DELETE_DEPS = "Cannot delete group as it has dependent product";

    public static final String PURCHASE_ORDER_NOT_FOUND = "Purchase order not found";
    public static final String PURCHASE_ORDER_DELETE_APPROVED = "Cannot delete approved purchase order";

    public static final String PURCHASE_REQUISITION_NOT_FOUND = "Purchase requisition not found";
    public static final String PURCHASE_REQUISITION_DELETE_APPROVED = "Cannot delete approved purchase requisition";

    public static final String ROLE_NOT_FOUND = "Role not found";
    public static final String ROLE_DELETE_DEPS = "Cannot delete role as it as has dependent user";

    public static final String SALES_ORDER_CATEGORY_DUPLICATE_CODE = "Sales order category code already exists.";
    public static final String SALES_ORDER_CATEGORY_NOT_FOUND = "Sales order category not found.";

    public static final String SECRET_INVALID = "The provided secret is invalid or unavailable";

    public static final String STOCK_TAKING_NOT_FOUND = "Stock taking not found";
    public static final String STOCK_TAKING_ITEM_NOT_FOUND = "Stock taking item not found";
    public static final String STOCK_TAKING_ALREADY_CLOSED = "Cannot delete closed stock taking";
    
    public static final String STORAGE_UPLOAD_ERROR = "An error occurred when uploading the picture";

    public static final String UNIT_MEASUREMENT_BAD_CONVERSION = "Invalid or unsupported unit of measurement conversion.";
    public static final String UNIT_MEASUREMENT_NOT_FOUND = "Unit of measurement not found";

    public static final String USER_EMAIL_IN_USE = "Email already in use";
    public static final String USER_LOGIN_BAD_CREDENTIALS = "User not found or invalid credentials";
    public static final String USER_NICKNAME_IN_USE = "Nickname already in use";
    public static final String USER_NOT_FOUND = "User not found";

    public static final String WAREHOUSE_NOT_FOUND = "Warehouse not found";
    public static final String WAREHOUSE_HAS_PENDING_ORDERS = "Cannot delete warehouse as it has pending orders";

    public static final String VENDOR_NOT_FOUND = "Vendor not found";

    public static final String PARAMETER_DELETE_IS_ACTIVE = "a";

  }

  public static class ExternalServices {
    public static class Urls {
      public static final String VIACEP = "https://viacep.com.br/ws/{cep}/json/";
      public static final String OPEN_EXCHANGE_RATES_CONVERT = "https://openexchangerates.org/api/convert/{}/{}/{}?app_id={}&prettyprint=false";
    }
  }

  public static class Pagination {
    public static final String DEFAULT_PAGE = "0";
    public static final String DEFAULT_SIZE = "5";
    public static final Integer MAX_PAGE_SIZE = 100;
  }

  public static class Parameters {
    public static final String DB_INITIALIZED_KEY = "db_initialized";
  }

  public static class Security {
    public static final long ACCESS_TOKEN_EXPIRATION_TIME = (60 * 60) * 10; // 10 hours
    public static final long REFRESH_TOKEN_EXPIRATION_TIME = 60 * 60 * 24 * 1; // (60 * 60 * 24) * 7; // 7 days
    public static final String ACCESS_JWT_SECRET = "WDo3aLJeuou7EEFIUQqcK8TpK9qG3TvePQ+dvqSUcua3urAIRG+nleU2c+UgmgTQ";
    public static final String REFRESH_JWT_SECRET = "rqWoQkseVZNPH+J1BZ2utQahvPzYitTkh/Ysu7pLjK9u/Kmq6ZKknXmNj82I/h03";
  }

  public static class Storage {
    public static final String USER_PIC_DIR = "uploads";
  }
}

class PurchaseState {
  static const CREATED = "CREATED";
  static const INVOICE_CREATED = "INVOICE_CREATED";
  static const CONFIRMED = "CONFIRMED";
  static const PAID = "PAID";
  static const CANCELLED = "CANCELLED";
  static const CONSUMED = "CONSUMED";
  static const CLOSED = "CLOSED";
}

class PaymentFinishCode {
  static const SUCCESSFUL_PAYMENT = "SUCCESSFUL_PAYMENT";
  static const CLOSED_BY_USER = "CLOSED_BY_USER";
  static const UNHANDLED_FORM_ERROR = "UNHANDLED_FORM_ERROR";
  static const PAYMENT_TIMEOUT = "PAYMENT_TIMEOUT";
  static const DECLINED_BY_SERVER = "DECLINED_BY_SERVER";
  static const RESULT_UNKNOWN = "RESULT_UNKNOWN";
}

class ProductStatus {
  static const ACTIVE = "ACTIVE";
  static const INACTIVE = "INACTIVE";
}

import 'package:pigeon/pigeon.dart';

class SubscriptionPeriod {
  late int years;
  late int months;
  late int days;
}

class Subscription {
  late SubscriptionPeriod? subscriptionPeriod;
  late SubscriptionPeriod? freeTrialPeriod;
  late SubscriptionPeriod? gracePeriod;
  late String? introductoryPrice;
  late String? introductoryPriceAmount;
  late SubscriptionPeriod? introductoryPricePeriod;
}

class Product {
  late String productId;
  late String? productType;
  late String productStatus;
  late String? priceLabel;
  late int? price;
  late String? currency;
  late String? language;
  late String? title;
  late String? description;
  late String? imageUrl;
  late String? promoImageUrl;
  late Subscription? subscription;
}

class ProductsResponse {
  late int code;
  late String? errorMessage;
  late String? errorDescription;
  late String? traceId;
  late List<Product?> products;
  late List<DigitalShopGeneralError?> errors;
}

class Purchase {
  late String? purchaseId;
  late String? productId;
  late String? description;
  late String? language;
  late String? purchaseTime;
  late String? orderId;
  late String? amountLabel;
  late int? amount;
  late String? currency;
  late int? quantity;
  late String? purchaseState;
  late String? developerPayload;
}

class PurchasesResponse {
  late int code;
  late String? errorMessage;
  late String? errorDescription;
  late String? traceId;
  late List<Purchase?> purchases;
  late List<DigitalShopGeneralError?> errors;
}

class ConfirmPurchaseResponse {
  late int code;
  late String? errorMessage;
  late String? errorDescription;
  late String? traceId;
  late List<DigitalShopGeneralError?> errors;
}

class PaymentResult {
  late SuccessInvoice? successInvoice;
  late InvalidInvoice? invalidInvoice;
  late SuccessPurchase? successPurchase;
  late InvalidPurchase? invalidPurchase;
}

class SuccessInvoice {
  late String invoiceId;
  late String finishCode;
}

class InvalidInvoice {
  late String? invoiceId;
}

class SuccessPurchase {
  late String finishCode;
  late String? orderId;
  late String purchaseId;
  late String productId;
}

class InvalidPurchase {
  late String? purchaseId;
  late String? invoiceId;
  late String? orderId;
  late int? quantity;
  late String? productId;
  late int? errorCode;
}

class DigitalShopGeneralError {
  late String? name;
  late int? code;
  late String? description;
}

@HostApi()
abstract class RustoreBilling {
  @async
  String initialize(String id, String prefix);

  @async
  bool available();

  @async
  ProductsResponse products(List<String?> ids);

  @async
  PurchasesResponse purchases();

  @async
  PaymentResult purchase(String id);

  @async
  ConfirmPurchaseResponse confirm(String id);
}

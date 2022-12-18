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
  late String id;
  late String? type;
  late String status;
  late String? label;
  late int? price;
  late String? currency;
  late String? language;
  late String? title;
  late String? description;
  late String? image;
  late String? promo;
  late Subscription? subscription;
}

class ProductsResponse {
  late int code;
  late String? errorMessage;
  late String? errorDescription;
  late String? traceId;
  late List<Product?> products;
}

class Purchase {
  late String? purchaseId;
  late String productId;
  late String? description;
  late String? language;
  late String? purchaseTime;
  late String? orderId;
  late String? amountLabel;
  late int? amount;
  late String? currency;
  late int? quantity;
  late String? state;
  late String? payload;
}

class PurchasesResponse {
  late int code;
  late String errorMessage;
  late String errorDescription;
  late String traceId;
  late List<Purchase?> purchases;
}

class ConfirmPurchaseResponse {
  late int code;
}

class PaymentResult {
  late int code;
}

@HostApi()
abstract class Client {
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

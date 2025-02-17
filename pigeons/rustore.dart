import 'package:pigeon/pigeon.dart';

@ConfigurePigeon(PigeonOptions(
    dartOut: 'lib/pigeons/rustore.dart',
    dartOptions: DartOptions(),
    kotlinOut:
        'android/src/main/kotlin/ru/rustore/flutter_rustore_billing/pigeons/Rustore.kt',
    kotlinOptions: KotlinOptions()))

@Deprecated("Будет удалён в следующей версии SDK")
class PurchaseAvailabilityResultFlutter {
  bool? isAvailable;
  bool? isUnknown;
  String? unavailableCause;

  PurchaseAvailabilityResultFlutter({
    this.isAvailable,
    this.isUnknown,
    this.unavailableCause,
  });
}

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
  late int? code;
  late String? errorMessage;
  late String? errorDescription;
  late String? traceId;
  late List<Product?> products;
  late List<DigitalShopGeneralError?> errors;
}

class Purchase {
  late String? purchaseId;
  late String? productId;
  late String? productType;
  late String? language;
  late String? purchaseTime;
  late String? orderId;
  late String? amountLabel;
  late int? amount;
  late String? currency;
  late int? quantity;
  late String? purchaseState;
  late String? developerPayload;
  late String? invoiceId;
  late String? subscriptionToken;
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
  late bool success;
  late String? errorMessage;
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
  late String? finishCode;
  late String? orderId;
  late String purchaseId;
  late String productId;
  late String? invoiceId;
  late String? subscriptionToken;
  late bool? sandbox;
}

class InvalidPurchase {
  late String? purchaseId;
  late String? invoiceId;
  late String? orderId;
  late int? quantity;
  late String? productId;
  late int? errorCode;
  late bool? sandbox;
}

class DigitalShopGeneralError {
  late String? name;
  late int? code;
  late String? description;
}

@HostApi()
abstract class RustoreBilling {
  @async
  String initialize(String id, String prefix, bool debugLogs);

  @Deprecated(
      "Данный метод работает только для флоу с авторизированным пользователем в RuStore")
  @async
  PurchaseAvailabilityResultFlutter available();

  @async
  ProductsResponse products(List<String?> ids);

  @async
  PurchasesResponse purchases();

  @async
  PaymentResult purchase(String id, String? developerPayload);

  @async
  Purchase purchaseInfo(String id);

  @async
  ConfirmPurchaseResponse confirm(String id);

  @async
  void deletePurchase(String purchaseId);

  @Deprecated("Будет удалён в следующей версии SDK")
  void offNativeErrorHandling();

  bool isRustoreInstalled();

  @async
  bool getAuthorizationStatus();
}

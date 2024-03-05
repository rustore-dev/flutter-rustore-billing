// Autogenerated from Pigeon (v4.2.14), do not edit directly.
// See also: https://pub.dev/packages/pigeon
// ignore_for_file: public_member_api_docs, non_constant_identifier_names, avoid_as, unused_import, unnecessary_parenthesis, prefer_null_aware_operators, omit_local_variable_types, unused_shown_name, unnecessary_import
import 'dart:async';
import 'dart:typed_data' show Float64List, Int32List, Int64List, Uint8List;

import 'package:flutter/foundation.dart' show ReadBuffer, WriteBuffer;
import 'package:flutter/services.dart';

class SubscriptionPeriod {
  SubscriptionPeriod({
    required this.years,
    required this.months,
    required this.days,
  });

  int years;

  int months;

  int days;

  Object encode() {
    return <Object?>[
      years,
      months,
      days,
    ];
  }

  static SubscriptionPeriod decode(Object result) {
    result as List<Object?>;
    return SubscriptionPeriod(
      years: result[0]! as int,
      months: result[1]! as int,
      days: result[2]! as int,
    );
  }
}

class Subscription {
  Subscription({
    this.subscriptionPeriod,
    this.freeTrialPeriod,
    this.gracePeriod,
    this.introductoryPrice,
    this.introductoryPriceAmount,
    this.introductoryPricePeriod,
  });

  SubscriptionPeriod? subscriptionPeriod;

  SubscriptionPeriod? freeTrialPeriod;

  SubscriptionPeriod? gracePeriod;

  String? introductoryPrice;

  String? introductoryPriceAmount;

  SubscriptionPeriod? introductoryPricePeriod;

  Object encode() {
    return <Object?>[
      subscriptionPeriod?.encode(),
      freeTrialPeriod?.encode(),
      gracePeriod?.encode(),
      introductoryPrice,
      introductoryPriceAmount,
      introductoryPricePeriod?.encode(),
    ];
  }

  static Subscription decode(Object result) {
    result as List<Object?>;
    return Subscription(
      subscriptionPeriod: result[0] != null
          ? SubscriptionPeriod.decode(result[0]! as List<Object?>)
          : null,
      freeTrialPeriod: result[1] != null
          ? SubscriptionPeriod.decode(result[1]! as List<Object?>)
          : null,
      gracePeriod: result[2] != null
          ? SubscriptionPeriod.decode(result[2]! as List<Object?>)
          : null,
      introductoryPrice: result[3] as String?,
      introductoryPriceAmount: result[4] as String?,
      introductoryPricePeriod: result[5] != null
          ? SubscriptionPeriod.decode(result[5]! as List<Object?>)
          : null,
    );
  }
}

class Product {
  Product({
    required this.productId,
    this.productType,
    required this.productStatus,
    this.priceLabel,
    this.price,
    this.currency,
    this.language,
    this.title,
    this.description,
    this.imageUrl,
    this.promoImageUrl,
    this.subscription,
  });

  String productId;

  String? productType;

  String productStatus;

  String? priceLabel;

  int? price;

  String? currency;

  String? language;

  String? title;

  String? description;

  String? imageUrl;

  String? promoImageUrl;

  Subscription? subscription;

  Object encode() {
    return <Object?>[
      productId,
      productType,
      productStatus,
      priceLabel,
      price,
      currency,
      language,
      title,
      description,
      imageUrl,
      promoImageUrl,
      subscription?.encode(),
    ];
  }

  static Product decode(Object result) {
    result as List<Object?>;
    return Product(
      productId: result[0]! as String,
      productType: result[1] as String?,
      productStatus: result[2]! as String,
      priceLabel: result[3] as String?,
      price: result[4] as int?,
      currency: result[5] as String?,
      language: result[6] as String?,
      title: result[7] as String?,
      description: result[8] as String?,
      imageUrl: result[9] as String?,
      promoImageUrl: result[10] as String?,
      subscription: result[11] != null
          ? Subscription.decode(result[11]! as List<Object?>)
          : null,
    );
  }
}

class ProductsResponse {
  ProductsResponse({
    this.code,
    this.errorMessage,
    this.errorDescription,
    this.traceId,
    required this.products,
    required this.errors,
  });

  int? code;

  String? errorMessage;

  String? errorDescription;

  String? traceId;

  List<Product?> products;

  List<DigitalShopGeneralError?> errors;

  Object encode() {
    return <Object?>[
      code,
      errorMessage,
      errorDescription,
      traceId,
      products,
      errors,
    ];
  }

  static ProductsResponse decode(Object result) {
    result as List<Object?>;
    return ProductsResponse(
      code: result[0] as int?,
      errorMessage: result[1] as String?,
      errorDescription: result[2] as String?,
      traceId: result[3] as String?,
      products: (result[4] as List<Object?>?)!.cast<Product?>(),
      errors: (result[5] as List<Object?>?)!.cast<DigitalShopGeneralError?>(),
    );
  }
}

class Purchase {
  Purchase({
    this.purchaseId,
    this.productId,
    this.description,
    this.language,
    this.purchaseTime,
    this.orderId,
    this.amountLabel,
    this.amount,
    this.currency,
    this.quantity,
    this.purchaseState,
    this.developerPayload,
    this.invoiceId,
    this.subscriptionToken,
  });

  String? purchaseId;

  String? productId;

  String? description;

  String? language;

  String? purchaseTime;

  String? orderId;

  String? amountLabel;

  int? amount;

  String? currency;

  int? quantity;

  String? purchaseState;

  String? developerPayload;

  String? invoiceId;

  String? subscriptionToken;

  Object encode() {
    return <Object?>[
      purchaseId,
      productId,
      description,
      language,
      purchaseTime,
      orderId,
      amountLabel,
      amount,
      currency,
      quantity,
      purchaseState,
      developerPayload,
      invoiceId,
      subscriptionToken,
    ];
  }

  static Purchase decode(Object result) {
    result as List<Object?>;
    return Purchase(
      purchaseId: result[0] as String?,
      productId: result[1] as String?,
      description: result[2] as String?,
      language: result[3] as String?,
      purchaseTime: result[4] as String?,
      orderId: result[5] as String?,
      amountLabel: result[6] as String?,
      amount: result[7] as int?,
      currency: result[8] as String?,
      quantity: result[9] as int?,
      purchaseState: result[10] as String?,
      developerPayload: result[11] as String?,
      invoiceId: result[12] as String?,
      subscriptionToken: result[13] as String?,
    );
  }
}

class PurchasesResponse {
  PurchasesResponse({
    required this.code,
    this.errorMessage,
    this.errorDescription,
    this.traceId,
    required this.purchases,
    required this.errors,
  });

  int code;

  String? errorMessage;

  String? errorDescription;

  String? traceId;

  List<Purchase?> purchases;

  List<DigitalShopGeneralError?> errors;

  Object encode() {
    return <Object?>[
      code,
      errorMessage,
      errorDescription,
      traceId,
      purchases,
      errors,
    ];
  }

  static PurchasesResponse decode(Object result) {
    result as List<Object?>;
    return PurchasesResponse(
      code: result[0]! as int,
      errorMessage: result[1] as String?,
      errorDescription: result[2] as String?,
      traceId: result[3] as String?,
      purchases: (result[4] as List<Object?>?)!.cast<Purchase?>(),
      errors: (result[5] as List<Object?>?)!.cast<DigitalShopGeneralError?>(),
    );
  }
}

class ConfirmPurchaseResponse {
  ConfirmPurchaseResponse({
    required this.success,
    this.errorMessage,
  });

  bool success;

  String? errorMessage;

  Object encode() {
    return <Object?>[
      success,
      errorMessage,
    ];
  }

  static ConfirmPurchaseResponse decode(Object result) {
    result as List<Object?>;
    return ConfirmPurchaseResponse(
      success: result[0]! as bool,
      errorMessage: result[1] as String?,
    );
  }
}

class PaymentResult {
  PaymentResult({
    this.successInvoice,
    this.invalidInvoice,
    this.successPurchase,
    this.invalidPurchase,
  });

  SuccessInvoice? successInvoice;

  InvalidInvoice? invalidInvoice;

  SuccessPurchase? successPurchase;

  InvalidPurchase? invalidPurchase;

  Object encode() {
    return <Object?>[
      successInvoice?.encode(),
      invalidInvoice?.encode(),
      successPurchase?.encode(),
      invalidPurchase?.encode(),
    ];
  }

  static PaymentResult decode(Object result) {
    result as List<Object?>;
    return PaymentResult(
      successInvoice: result[0] != null
          ? SuccessInvoice.decode(result[0]! as List<Object?>)
          : null,
      invalidInvoice: result[1] != null
          ? InvalidInvoice.decode(result[1]! as List<Object?>)
          : null,
      successPurchase: result[2] != null
          ? SuccessPurchase.decode(result[2]! as List<Object?>)
          : null,
      invalidPurchase: result[3] != null
          ? InvalidPurchase.decode(result[3]! as List<Object?>)
          : null,
    );
  }
}

class SuccessInvoice {
  SuccessInvoice({
    required this.invoiceId,
    required this.finishCode,
  });

  String invoiceId;

  String finishCode;

  Object encode() {
    return <Object?>[
      invoiceId,
      finishCode,
    ];
  }

  static SuccessInvoice decode(Object result) {
    result as List<Object?>;
    return SuccessInvoice(
      invoiceId: result[0]! as String,
      finishCode: result[1]! as String,
    );
  }
}

class InvalidInvoice {
  InvalidInvoice({
    this.invoiceId,
  });

  String? invoiceId;

  Object encode() {
    return <Object?>[
      invoiceId,
    ];
  }

  static InvalidInvoice decode(Object result) {
    result as List<Object?>;
    return InvalidInvoice(
      invoiceId: result[0] as String?,
    );
  }
}

class SuccessPurchase {
  SuccessPurchase({
    this.finishCode,
    this.orderId,
    required this.purchaseId,
    required this.productId,
    this.invoiceId,
    this.subscriptionToken,
  });

  String? finishCode;

  String? orderId;

  String purchaseId;

  String productId;

  String? invoiceId;

  String? subscriptionToken;

  Object encode() {
    return <Object?>[
      finishCode,
      orderId,
      purchaseId,
      productId,
      invoiceId,
      subscriptionToken,
    ];
  }

  static SuccessPurchase decode(Object result) {
    result as List<Object?>;
    return SuccessPurchase(
      finishCode: result[0] as String?,
      orderId: result[1] as String?,
      purchaseId: result[2]! as String,
      productId: result[3]! as String,
      invoiceId: result[4] as String?,
      subscriptionToken: result[5] as String?,
    );
  }
}

class InvalidPurchase {
  InvalidPurchase({
    this.purchaseId,
    this.invoiceId,
    this.orderId,
    this.quantity,
    this.productId,
    this.errorCode,
  });

  String? purchaseId;

  String? invoiceId;

  String? orderId;

  int? quantity;

  String? productId;

  int? errorCode;

  Object encode() {
    return <Object?>[
      purchaseId,
      invoiceId,
      orderId,
      quantity,
      productId,
      errorCode,
    ];
  }

  static InvalidPurchase decode(Object result) {
    result as List<Object?>;
    return InvalidPurchase(
      purchaseId: result[0] as String?,
      invoiceId: result[1] as String?,
      orderId: result[2] as String?,
      quantity: result[3] as int?,
      productId: result[4] as String?,
      errorCode: result[5] as int?,
    );
  }
}

class DigitalShopGeneralError {
  DigitalShopGeneralError({
    this.name,
    this.code,
    this.description,
  });

  String? name;

  int? code;

  String? description;

  Object encode() {
    return <Object?>[
      name,
      code,
      description,
    ];
  }

  static DigitalShopGeneralError decode(Object result) {
    result as List<Object?>;
    return DigitalShopGeneralError(
      name: result[0] as String?,
      code: result[1] as int?,
      description: result[2] as String?,
    );
  }
}

class _RustoreBillingCodec extends StandardMessageCodec {
  const _RustoreBillingCodec();
  @override
  void writeValue(WriteBuffer buffer, Object? value) {
    if (value is ConfirmPurchaseResponse) {
      buffer.putUint8(128);
      writeValue(buffer, value.encode());
    } else if (value is DigitalShopGeneralError) {
      buffer.putUint8(129);
      writeValue(buffer, value.encode());
    } else if (value is InvalidInvoice) {
      buffer.putUint8(130);
      writeValue(buffer, value.encode());
    } else if (value is InvalidPurchase) {
      buffer.putUint8(131);
      writeValue(buffer, value.encode());
    } else if (value is PaymentResult) {
      buffer.putUint8(132);
      writeValue(buffer, value.encode());
    } else if (value is Product) {
      buffer.putUint8(133);
      writeValue(buffer, value.encode());
    } else if (value is ProductsResponse) {
      buffer.putUint8(134);
      writeValue(buffer, value.encode());
    } else if (value is Purchase) {
      buffer.putUint8(135);
      writeValue(buffer, value.encode());
    } else if (value is PurchasesResponse) {
      buffer.putUint8(136);
      writeValue(buffer, value.encode());
    } else if (value is Subscription) {
      buffer.putUint8(137);
      writeValue(buffer, value.encode());
    } else if (value is SubscriptionPeriod) {
      buffer.putUint8(138);
      writeValue(buffer, value.encode());
    } else if (value is SuccessInvoice) {
      buffer.putUint8(139);
      writeValue(buffer, value.encode());
    } else if (value is SuccessPurchase) {
      buffer.putUint8(140);
      writeValue(buffer, value.encode());
    } else {
      super.writeValue(buffer, value);
    }
  }

  @override
  Object? readValueOfType(int type, ReadBuffer buffer) {
    switch (type) {
      case 128:       
        return ConfirmPurchaseResponse.decode(readValue(buffer)!);
      
      case 129:       
        return DigitalShopGeneralError.decode(readValue(buffer)!);
      
      case 130:       
        return InvalidInvoice.decode(readValue(buffer)!);
      
      case 131:       
        return InvalidPurchase.decode(readValue(buffer)!);
      
      case 132:       
        return PaymentResult.decode(readValue(buffer)!);
      
      case 133:       
        return Product.decode(readValue(buffer)!);
      
      case 134:       
        return ProductsResponse.decode(readValue(buffer)!);
      
      case 135:       
        return Purchase.decode(readValue(buffer)!);
      
      case 136:       
        return PurchasesResponse.decode(readValue(buffer)!);
      
      case 137:       
        return Subscription.decode(readValue(buffer)!);
      
      case 138:       
        return SubscriptionPeriod.decode(readValue(buffer)!);
      
      case 139:       
        return SuccessInvoice.decode(readValue(buffer)!);
      
      case 140:       
        return SuccessPurchase.decode(readValue(buffer)!);
      
      default:

        return super.readValueOfType(type, buffer);
      
    }
  }
}

class RustoreBilling {
  /// Constructor for [RustoreBilling].  The [binaryMessenger] named argument is
  /// available for dependency injection.  If it is left null, the default
  /// BinaryMessenger will be used which routes to the host platform.
  RustoreBilling({BinaryMessenger? binaryMessenger})
      : _binaryMessenger = binaryMessenger;
  final BinaryMessenger? _binaryMessenger;

  static const MessageCodec<Object?> codec = _RustoreBillingCodec();

  Future<String> initialize(String arg_id, String arg_prefix, bool arg_debugLogs, bool? arg_allowNativeErrorHadling) async {
    final BasicMessageChannel<Object?> channel = BasicMessageChannel<Object?>(
        'dev.flutter.pigeon.RustoreBilling.initialize', codec,
        binaryMessenger: _binaryMessenger);
    final List<Object?>? replyList =
        await channel.send(<Object?>[arg_id, arg_prefix, arg_debugLogs, arg_allowNativeErrorHadling]) as List<Object?>?;
    if (replyList == null) {
      throw PlatformException(
        code: 'channel-error',
        message: 'Unable to establish connection on channel.',
      );
    } else if (replyList.length > 1) {
      throw PlatformException(
        code: replyList[0]! as String,
        message: replyList[1] as String?,
        details: replyList[2],
      );
    } else if (replyList[0] == null) {
      throw PlatformException(
        code: 'null-error',
        message: 'Host platform returned null value for non-null return value.',
      );
    } else {
      return (replyList[0] as String?)!;
    }
  }

  Future<bool> available() async {
    final BasicMessageChannel<Object?> channel = BasicMessageChannel<Object?>(
        'dev.flutter.pigeon.RustoreBilling.available', codec,
        binaryMessenger: _binaryMessenger);
    final List<Object?>? replyList =
        await channel.send(null) as List<Object?>?;
    if (replyList == null) {
      throw PlatformException(
        code: 'channel-error',
        message: 'Unable to establish connection on channel.',
      );
    } else if (replyList.length > 1) {
      throw PlatformException(
        code: replyList[0]! as String,
        message: replyList[1] as String?,
        details: replyList[2],
      );
    } else if (replyList[0] == null) {
      throw PlatformException(
        code: 'null-error',
        message: 'Host platform returned null value for non-null return value.',
      );
    } else {
      return (replyList[0] as bool?)!;
    }
  }

  Future<ProductsResponse> products(List<String?> arg_ids) async {
    final BasicMessageChannel<Object?> channel = BasicMessageChannel<Object?>(
        'dev.flutter.pigeon.RustoreBilling.products', codec,
        binaryMessenger: _binaryMessenger);
    final List<Object?>? replyList =
        await channel.send(<Object?>[arg_ids]) as List<Object?>?;
    if (replyList == null) {
      throw PlatformException(
        code: 'channel-error',
        message: 'Unable to establish connection on channel.',
      );
    } else if (replyList.length > 1) {
      throw PlatformException(
        code: replyList[0]! as String,
        message: replyList[1] as String?,
        details: replyList[2],
      );
    } else if (replyList[0] == null) {
      throw PlatformException(
        code: 'null-error',
        message: 'Host platform returned null value for non-null return value.',
      );
    } else {
      return (replyList[0] as ProductsResponse?)!;
    }
  }

  Future<PurchasesResponse> purchases() async {
    final BasicMessageChannel<Object?> channel = BasicMessageChannel<Object?>(
        'dev.flutter.pigeon.RustoreBilling.purchases', codec,
        binaryMessenger: _binaryMessenger);
    final List<Object?>? replyList =
        await channel.send(null) as List<Object?>?;
    if (replyList == null) {
      throw PlatformException(
        code: 'channel-error',
        message: 'Unable to establish connection on channel.',
      );
    } else if (replyList.length > 1) {
      throw PlatformException(
        code: replyList[0]! as String,
        message: replyList[1] as String?,
        details: replyList[2],
      );
    } else if (replyList[0] == null) {
      throw PlatformException(
        code: 'null-error',
        message: 'Host platform returned null value for non-null return value.',
      );
    } else {
      return (replyList[0] as PurchasesResponse?)!;
    }
  }

  Future<PaymentResult> purchase(String arg_id, String? arg_developerPayload) async {
    final BasicMessageChannel<Object?> channel = BasicMessageChannel<Object?>(
        'dev.flutter.pigeon.RustoreBilling.purchase', codec,
        binaryMessenger: _binaryMessenger);
    final List<Object?>? replyList =
        await channel.send(<Object?>[arg_id, arg_developerPayload]) as List<Object?>?;
    if (replyList == null) {
      throw PlatformException(
        code: 'channel-error',
        message: 'Unable to establish connection on channel.',
      );
    } else if (replyList.length > 1) {
      throw PlatformException(
        code: replyList[0]! as String,
        message: replyList[1] as String?,
        details: replyList[2],
      );
    } else if (replyList[0] == null) {
      throw PlatformException(
        code: 'null-error',
        message: 'Host platform returned null value for non-null return value.',
      );
    } else {
      return (replyList[0] as PaymentResult?)!;
    }
  }

  Future<Purchase> purchaseInfo(String arg_id) async {
    final BasicMessageChannel<Object?> channel = BasicMessageChannel<Object?>(
        'dev.flutter.pigeon.RustoreBilling.purchaseInfo', codec,
        binaryMessenger: _binaryMessenger);
    final List<Object?>? replyList =
        await channel.send(<Object?>[arg_id]) as List<Object?>?;
    if (replyList == null) {
      throw PlatformException(
        code: 'channel-error',
        message: 'Unable to establish connection on channel.',
      );
    } else if (replyList.length > 1) {
      throw PlatformException(
        code: replyList[0]! as String,
        message: replyList[1] as String?,
        details: replyList[2],
      );
    } else if (replyList[0] == null) {
      throw PlatformException(
        code: 'null-error',
        message: 'Host platform returned null value for non-null return value.',
      );
    } else {
      return (replyList[0] as Purchase?)!;
    }
  }

  Future<ConfirmPurchaseResponse> confirm(String arg_id) async {
    final BasicMessageChannel<Object?> channel = BasicMessageChannel<Object?>(
        'dev.flutter.pigeon.RustoreBilling.confirm', codec,
        binaryMessenger: _binaryMessenger);
    final List<Object?>? replyList =
        await channel.send(<Object?>[arg_id]) as List<Object?>?;
    if (replyList == null) {
      throw PlatformException(
        code: 'channel-error',
        message: 'Unable to establish connection on channel.',
      );
    } else if (replyList.length > 1) {
      throw PlatformException(
        code: replyList[0]! as String,
        message: replyList[1] as String?,
        details: replyList[2],
      );
    } else if (replyList[0] == null) {
      throw PlatformException(
        code: 'null-error',
        message: 'Host platform returned null value for non-null return value.',
      );
    } else {
      return (replyList[0] as ConfirmPurchaseResponse?)!;
    }
  }
}

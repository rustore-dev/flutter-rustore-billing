import 'dart:async';
import 'package:flutter_rustore_billing/pigeons/rustore.dart';

class RustoreBillingClient {
  static final RustoreBilling _api = RustoreBilling();

  static Future<String> initialize(
      String id, String deeplinkScheme, bool debugLogs) async {
    return _api.initialize(id, deeplinkScheme, debugLogs);
  }

  static Future<bool> getAuthorizationStatus () async {
    return _api.getAuthorizationStatus();
  }

  @Deprecated(
      "Данный метод работает только для флоу с авторизированным пользователем в RuStore")
  static Future<PurchaseAvailabilityResult> available() async {
    final result = await _api.available();

    if (result.isAvailable == true) return const Available();
    if (result.isUnknown == true) return const Unknown();
    if (result.unavailableCause != null) {
      return Unavailable(result.unavailableCause!);
    }
    throw Exception("Invalid result");
  }

  static Future<ProductsResponse> products(List<String?> ids) async {
    final result = await _api.products(ids);

    return result;
  }

  static Future<PaymentResult> purchase(
      String id, String? developerPayload) async {
    final result = _api.purchase(id, developerPayload);

    return result;
  }

  static Future<PurchasesResponse> purchases() async {
    final result = _api.purchases();

    return result;
  }

  static Future<ConfirmPurchaseResponse> confirm(String id) async {
    final result = _api.confirm(id);

    return result;
  }

  static Future<Purchase> purchaseInfo(String id) async {
    final result = _api.purchaseInfo(id);
    return result;
  }

  static Future<void> deletePurchase(String id) async {
    return _api.deletePurchase(id);
  }

  static Future<bool> isRustoreInstalled() async {
    return _api.isRustoreInstalled();
  }
}

enum PurchaseAvailabilityType { available, unknown, unavailable }

abstract class PurchaseAvailabilityResult {
  final PurchaseAvailabilityType type;
  final String? cause;
  const PurchaseAvailabilityResult({
    required this.type,
    this.cause,
  });

  T when<T>({
    required T Function() available,
    required T Function() unknown,
    required T Function(String) unavailable,
  }) {
    switch (type) {
      case PurchaseAvailabilityType.available:
        return available();
      case PurchaseAvailabilityType.unknown:
        return unknown();
      case PurchaseAvailabilityType.unavailable:
        return unavailable(cause ?? "unavailable error");
    }
  }
}

class Available extends PurchaseAvailabilityResult {
  const Available() : super(type: PurchaseAvailabilityType.available);
}

class Unknown extends PurchaseAvailabilityResult {
  const Unknown() : super(type: PurchaseAvailabilityType.unknown);
}

class Unavailable extends PurchaseAvailabilityResult {
  const Unavailable(String cause) : super(type: PurchaseAvailabilityType.unavailable, cause: cause);
}

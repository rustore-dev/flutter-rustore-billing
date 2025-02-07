import 'dart:async';
import 'package:flutter_rustore_billing/pigeons/rustore.dart';

abstract class PurchaseAvailabilityResultFlutter {
  const PurchaseAvailabilityResultFlutter();

  T when<T>({
    required T Function() available,
    required T Function() unknown,
    required T Function(RuStoreExceptionFlutter) unavailable,
  });
}

class Available extends PurchaseAvailabilityResultFlutter {
  const Available();

  @override
  T when<T>({
    required T Function() available,
    required T Function() unknown,
    required T Function(RuStoreExceptionFlutter) unavailable,
  }) =>
      available();
}

class Unknown extends PurchaseAvailabilityResultFlutter {
  const Unknown();

  @override
  T when<T>({
    required T Function() available,
    required T Function() unknown,
    required T Function(RuStoreExceptionFlutter) unavailable,
  }) =>
      unknown();
}

class Unavailable extends PurchaseAvailabilityResultFlutter {
  final RuStoreExceptionFlutter cause;

  const Unavailable(this.cause);

  @override
  T when<T>({
    required T Function() available,
    required T Function() unknown,
    required T Function(RuStoreExceptionFlutter) unavailable,
  }) =>
      unavailable(cause);
}

class RustoreBillingClient {
  static final RustoreBilling _api = RustoreBilling();

  static Future<String> initialize(
      String id, String deeplinkScheme, bool debugLogs) async {
    return _api.initialize(id, deeplinkScheme, debugLogs);
  }

  static Future<PurchaseAvailabilityResultFlutter> available() async {
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

  static Future<void> offNativeErrorHandling() async {
    return _api.offNativeErrorHandling();
  }

  static Future<bool> isRustoreInstalled() async {
    return _api.isRustoreInstalled();
  }
}

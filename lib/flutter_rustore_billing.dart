import 'dart:async';
import 'package:receive_intent/receive_intent.dart';
import 'package:flutter_rustore_billing/pigeons/rustore.dart';

class RustoreBillingClient {
  static final RustoreBilling _api = RustoreBilling();

  static Future<String> initialize(
      String id, String prefix, bool debugLogs) async {
    return _api.initialize(id, prefix, debugLogs);
  }

  static Future<bool> available() async {
    final result = await _api.available();

    return result;
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

  StreamSubscription? sub;

  Future<void> onNewIntent() async {
    // final receivedIntent = await ReceiveIntent.getInitialIntent();

    sub = ReceiveIntent.receivedIntentStream.listen((Intent? intent) {
      _api.onNewIntent(intent.action);
    }, onError: (err) {});
  }
}

import 'package:flutter_rustore_billing/pigeons/rustore.dart';

class RustoreBillingClient {
  static RustoreBilling _api = RustoreBilling();

  static Future<String> initialize(String id, String prefix, {bool debug = false}) async {
    return _api.initialize(id, prefix, debug);
  }

  static Future<bool> available() async {
    final result = await _api.available();

    return result;
  }

  static Future<ProductsResponse> products(List<String?> ids) async {
    final result = await _api.products(ids);

    return result;
  }

  static Future<PaymentResult> purchase(String id) async {
    return _api.purchase(id);
  }

  static Future<PurchasesResponse> purchases() async {
    final result = _api.purchases();

    return result;
  }

  static Future<ConfirmPurchaseResponse> confirm(String id) async {
    final result = _api.confirm(id);

    return result;
  }
}

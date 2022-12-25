import 'package:flutter_rustore_billing/pigeons/rustore.dart';

class RustoreBillingClient {
  static Client _api = Client();

  static Future<String> initialize(String id, String prefix) async {
    return _api.initialize(id, prefix);
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
    final result = _api.purchase(id);

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
}

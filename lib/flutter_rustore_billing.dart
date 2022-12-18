import 'package:flutter_rustore_billing/pigeons/rustore.dart';

class RustoreBillingClient {
  static Client _api = Client();

  static Future<String> initialize(String id, String prefix) async {
    return _api.initialize(id, prefix);
  }

  static Future<bool> available() async {
    // @todo: handle exception
    final result = await _api.available();

    return result;
  }

  static Future<ProductsResponse> products(List<String?> ids) async {
    // @todo: handle exception
    final result = await _api.products(ids);

    return result;
  }
}

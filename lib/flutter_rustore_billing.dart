import 'package:flutter_rustore_billing/pigeons/rustore.dart';

class RustoreBillingClient {
  final _api = Client();

  Future<String> initialize(String id, String prefix) async {
    return _api.initialize(id, prefix);
  }

  Future<bool> available() async {
    // @todo: handle exception
    final result = await _api.available();

    return result;
  }
}

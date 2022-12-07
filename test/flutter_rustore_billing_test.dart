import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_rustore_billing/flutter_rustore_billing.dart';
import 'package:flutter_rustore_billing/flutter_rustore_billing_platform_interface.dart';
import 'package:flutter_rustore_billing/flutter_rustore_billing_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockFlutterRustoreBillingPlatform
    with MockPlatformInterfaceMixin
    implements FlutterRustoreBillingPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final FlutterRustoreBillingPlatform initialPlatform = FlutterRustoreBillingPlatform.instance;

  test('$MethodChannelFlutterRustoreBilling is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelFlutterRustoreBilling>());
  });

  test('getPlatformVersion', () async {
    FlutterRustoreBilling flutterRustoreBillingPlugin = FlutterRustoreBilling();
    MockFlutterRustoreBillingPlatform fakePlatform = MockFlutterRustoreBillingPlatform();
    FlutterRustoreBillingPlatform.instance = fakePlatform;

    expect(await flutterRustoreBillingPlugin.getPlatformVersion(), '42');
  });
}

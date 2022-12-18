import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter_rustore_billing/flutter_rustore_billing.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  void initState() {
    super.initState();
    billing();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> billing() async {
    RustoreBillingClient.initialize(
      "j102RQvuquyaoKnK1uPcGzSorQclWhKd",
      "yourappscheme://iamback",
    ).then((value) {
      print("initialize success: $value");
      RustoreBillingClient.available().then((value) {
        print("available $value");
      });

      RustoreBillingClient.products([]).then((response) {
        print(response.products);
      });
    }, onError: (err) {
      print("initialize err: $err");
      RustoreBillingClient.available().then((value) {
        print("available $value");
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Text('Running'),
        ),
      ),
    );
  }
}

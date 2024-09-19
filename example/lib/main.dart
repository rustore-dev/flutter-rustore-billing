import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter_rustore_billing/flutter_rustore_billing.dart';
import 'package:flutter_rustore_billing/pigeons/rustore.dart';
import 'package:flutter_rustore_billing_ex/PurchaseWidget.dart';
import 'package:flutter_rustore_billing_ex/ProductWidget.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> with SingleTickerProviderStateMixin {
  // define your tab controller here
  late TabController _tabController;

  List<Product?> products = [];
  List<Purchase?> purchases = [];
  PaymentResult? payment;
  ConfirmPurchaseResponse? confirmPurchaseResponse;
  Purchase? purchaseInformation;

  @override
  void initState() {
    // initialise your tab controller here
    _tabController = TabController(length: 2, vsync: this);
    super.initState();
    billing();
    // RustoreBillingClient.offNativeErrorHandling();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> billing() async {
    RustoreBillingClient.initialize("184050", "example", true).then((value) {
      print("initialize success: $value");
      RustoreBillingClient.available().then((value) {
        print("available $value");
      }, onError: (err) {
        print("available err: $err");
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
        leading: const Icon(
          Icons.menu,
          color: Colors.black,
        ),
        backgroundColor: Colors.transparent,
        elevation: 0,
        title: const Text(
          'Flutter RuStore Billing',
          style: TextStyle(color: Colors.black),
        ),
      ),
      body: Column(
        children: <Widget>[
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: FutureBuilder<bool>(
              future: RustoreBillingClient.isRustoreInstalled(),
              builder: (BuildContext context, AsyncSnapshot<bool> snapshot) {
                if (snapshot.connectionState == ConnectionState.waiting) {
                  return const CircularProgressIndicator(); // Показываем индикатор загрузки пока ждем
                } else if (snapshot.hasError) {
                  return Text(
                      'Error: ${snapshot.error}'); // Показываем ошибку, если она произошла
                } else {
                  bool isInstalled = snapshot.data ?? false;
                  return Text(
                      'Is RuStore installed: $isInstalled'); // Используем результат
                }
              },
            ),
          ),
          Padding(
            padding: const EdgeInsets.symmetric(vertical: 10, horizontal: 5),
            child: TabBar(
              controller: _tabController,
              labelColor: Colors.green,
              isScrollable: true,
              indicatorColor: Colors.transparent,
              unselectedLabelColor: Colors.grey,
              unselectedLabelStyle: const TextStyle(
                fontSize: 16,
                color: Colors.grey,
                fontWeight: FontWeight.w700,
              ),
              labelStyle: const TextStyle(
                fontSize: 16,
                fontWeight: FontWeight.w700,
              ),
              tabs: const <Widget>[
                Text('PRODUCTS'),
                Text('PURCHASES'),
              ],
            ),
          ),
          Expanded(
            child: TabBarView(
              controller: _tabController,
              children: const <Widget>[ProductWidget(), PurchaseWidget()],
            ),
          ),
        ],
      ),
    ));
  }
}

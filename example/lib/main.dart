import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter_rustore_billing/const.dart';
import 'package:flutter_rustore_billing/flutter_rustore_billing.dart';
import 'package:flutter_rustore_billing/pigeons/rustore.dart';

final List<String?> ids = [
  '1000_franklin_ver1',
  '10_tomatoes_ver1'
];

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  List<Product?> products = [];
  List<Purchase?> purchases = [];
  PaymentResult? payment;
  ConfirmPurchaseResponse? confirmPurchaseResponse;
  Purchase? purchaseInformation;

  @override
  void initState() {
    super.initState();
    billing();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> billing() async {
    RustoreBillingClient.initialize(
      "184047",
      "yourappscheme://iamback",
    ).then((value) {
      print("initialize success: $value");
      RustoreBillingClient.available().then((value) {
        print("available $value");
      }, onError: (err) {
        print("available err: $err");
      });

      // products
      reloadProducts();

      // purchases
      reloadPurchases();
    }, onError: (err) {
      print("initialize err: $err");
      RustoreBillingClient.available().then((value) {
        print("available $value");
      });
    });
  }

  void purchase(String id) {
    RustoreBillingClient.purchase(id).then((value) {
      setState(() {
        payment = value;
      });
    }, onError: (err) {
      print("purchase err: $err");
    });
  }

  void purchaseInfo(String id) {
    RustoreBillingClient.purchaseInfo(id).then((value) {
      setState(() {
        purchaseInformation = value;
      });
    }, onError: (err) {
      print("Error getPurchaseInfo: $err");
    });
  }

  void confirm(String id) {
    RustoreBillingClient.confirm(id).then((value) {
      setState(() {
        confirmPurchaseResponse = value;
      });
    }, onError: (err) {
      print("confirm err: $err");
    });
  }

  void reloadProducts() {
    RustoreBillingClient.products(ids).then((response) {
      setState(() {
        products = response.products ?? [];
      });

      for (final product in response.products) {
        print(product?.productId);
      }
    });
  }

  void reloadPurchases() {
    RustoreBillingClient.purchases().then((response) {
      setState(() {
        purchases = response.purchases ?? [];
      });

      for (final product in response.purchases) {
        print(product?.purchaseId);
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: SingleChildScrollView(
          child: Column(
            children: [
              Row(
                children: [
                  OutlinedButton(
                    onPressed: () {
                      reloadProducts();
                    },
                    child: const Text('Products'),
                  ),
                  OutlinedButton(
                    onPressed: () {
                      reloadPurchases();
                    },
                    child: const Text('Purchases'),
                  ),
                ],
              ),
              const Text('Payment'),
              if (payment?.successInvoice != null) Text("successInvoice: ${payment?.successInvoice?.invoiceId ?? '0'}"),
              if (payment?.invalidInvoice != null) Text("invalidInvoice: ${payment?.invalidInvoice?.invoiceId ?? '0'}"),
              if (payment?.successPurchase != null) Text("successPurchase: ${payment?.successPurchase?.purchaseId ?? '0'}"),
              if (payment?.invalidPurchase != null) Text("invalidPurchase: ${payment?.invalidPurchase?.purchaseId ?? '0'}"),
              const Text('Confirm'),
              if (confirmPurchaseResponse != null) Text("${confirmPurchaseResponse?.errorMessage}"),
              const Text('Purchase info(Purchase state)'),
              if (purchaseInformation != null) Text("${purchaseInformation?.purchaseState}"),
              const Text('Products'),
              for (var product in products) ...[
                Text("${product?.title ?? ""}: ${product?.productId ?? ""}"),
                OutlinedButton(
                  onPressed: () {
                    purchase(product?.productId ?? "");
                  },
                  child: const Text('Buy'),
                ),
              ],
              const Text('Purchases'),
              for (var purchase in purchases) ...[
                Text("${purchase?.description ?? ""}: ${purchase?.purchaseId ?? ""} - ${purchase?.purchaseState ?? ""}"),
                Text("${purchase?.description ?? ""}: ${purchase?.invoiceId ?? ""} - ${purchase?.subscriptionToken ?? ""}"),
                if ((purchase?.purchaseState ?? "") == PurchaseState.PAID)
                  Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      OutlinedButton(
                        onPressed: () {
                          confirm(purchase?.purchaseId ?? "");
                        },
                        child: const Text('Confirm'),
                      ),
                      OutlinedButton(
                          onPressed: () {
                            purchaseInfo(purchase?.purchaseId ?? "");
                          },
                          child: const Text('Purchase Info')
                      ),
                    ],
                  ),
              ],
            ],
          ),
        ),
      ),
    );
  }
}

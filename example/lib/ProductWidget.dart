import 'package:flutter/material.dart';

import 'package:flutter_rustore_billing/flutter_rustore_billing.dart';
import 'package:flutter_rustore_billing/pigeons/rustore.dart';

final List<String?> ids = [
  "product1",
  "product2",
  "product3",
];

class ProductWidget extends StatefulWidget {
  const ProductWidget({super.key});

  @override
  State<ProductWidget> createState() => _ProductWidget();
}

class _ProductWidget extends State<ProductWidget> {
  List<Product?> products = [];
  PaymentResult? payment;
  ConfirmPurchaseResponse? confirmPurchaseResponse;

  @override
  void initState() {
    super.initState();
    reloadProducts();
  }

  void purchase(String id, String? developerPayload) {
    RustoreBillingClient.purchase(id, developerPayload).then((value) {
      setState(() {
        payment = value;
      });
    }, onError: (err) {
      print("purchase err: $err");
    });
  }

  void confirm(String id) {
    RustoreBillingClient.confirm(id).then((value) {
      setState(() {
        confirmPurchaseResponse = value;
      });
      print(value.toString());
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

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      child: Column(
        children: [
          for (var product in products) ...[
            TextButton(
              onPressed: () {
                purchase(product?.productId ?? "", "developer");
              },
              child: Text(
                "Название:${product?.productId ?? ""} Тип:${product?.productType ?? ""} Цена:${product?.priceLabel ?? ""}",
                style: const TextStyle(fontWeight: FontWeight.bold),
                textAlign: TextAlign.center,
              ),
            ),
          ],
        ],
      ),
    );
  }
}

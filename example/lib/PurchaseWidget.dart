import 'package:flutter/material.dart';

import 'package:flutter_rustore_billing/const.dart';
import 'package:flutter_rustore_billing/flutter_rustore_billing.dart';
import 'package:flutter_rustore_billing/pigeons/rustore.dart';

class PurchaseWidget extends StatefulWidget {
  const PurchaseWidget({super.key});

  @override
  State<PurchaseWidget> createState() => _PurchaseWidget();
}

class _PurchaseWidget extends State<PurchaseWidget> {
  List<Purchase?> purchases = [];
  ConfirmPurchaseResponse? confirmPurchaseResponse;
  Purchase? purchaseInformation;

  @override
  void initState() {
    super.initState();
    reloadPurchases();
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

  void confirm(String id) {
    RustoreBillingClient.confirm(id).then((value) {
      setState(() {
        confirmPurchaseResponse = value;
      });
    }, onError: (err) {
      print("confirm err: $err");
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

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      child: Column(
        children: [
          for (var purchase in purchases) ...[
            Text(
                "${purchase?.productId ?? ""} - ${purchase?.purchaseState ?? ""}"),
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
                      child: const Text('Purchase Info')),
                ],
              ),
          ],
        ],
      ),
    );
  }
}

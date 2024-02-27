package ru.rustore.flutter_rustore_billing.utils

import android.content.Context
import ru.rustore.flutter_rustore_billing.pigeons.Rustore.DigitalShopGeneralError
import ru.rustore.sdk.billingclient.utils.resolveForBilling
import ru.rustore.sdk.core.exception.RuStoreException

class ckck {
    public fun resolveForBilling(error: DigitalShopGeneralError, context: Context) {
        RuStoreException(error.name.toString()).resolveForBilling(context);
    }
}
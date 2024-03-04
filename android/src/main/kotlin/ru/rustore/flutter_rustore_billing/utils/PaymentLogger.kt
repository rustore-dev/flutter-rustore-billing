package ru.rustore.flutter_rustore_billing.utils

import android.util.Log
import ru.rustore.sdk.billingclient.provider.logger.ExternalPaymentLogger

internal class PaymentLogger(private val tag: String): ExternalPaymentLogger {
    override fun d(e: Throwable?, message: () -> String) {
        Log.d(tag, message.invoke(), e)
    }

    override fun e(e: Throwable?, message: () -> String) {
        Log.e(tag, message.invoke(), e)
    }

    override fun i(e: Throwable?, message: () -> String) {
        Log.i(tag, message.invoke(), e)
    }

    override fun v(e: Throwable?, message: () -> String) {
        Log.v(tag, message.invoke(), e)
    }

    override fun w(e: Throwable?, message: () -> String) {
        Log.w(tag, message.invoke(), e)
    }
}
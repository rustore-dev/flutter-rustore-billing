package ru.rustore.flutter_rustore_billing.utils

import android.util.Log as l

object Log {
    fun i(msg: String, invoke: String, e: Throwable?) {
        l.i("FlutterRustoreBilling", msg)
    }

    fun d(msg: String, invoke: String, e: Throwable?) {
        l.d("FlutterRustoreBilling", msg)
    }
}
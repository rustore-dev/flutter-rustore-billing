package ru.rustore.flutter_rustore_billing.utils

import android.util.Log as l

object Log {
    fun i(msg: String) {
        l.i("FlutterRustoreBilling", msg)
    }

    fun d(msg: String) {
        l.d("FlutterRustoreBilling", msg)
    }
}
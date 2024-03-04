package ru.rustore.flutter_rustore_billing

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.annotation.NonNull

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.PluginRegistry.NewIntentListener
import ru.rustore.flutter_rustore_billing.pigeons.Rustore

/** FlutterRustoreBillingPlugin */
class FlutterRustoreBillingPlugin : FlutterPlugin, ActivityAware, NewIntentListener {
    private lateinit var context: Context
    private lateinit var application: Application
    private var client = FlutterRustoreBillingClient(application)

    override fun onAttachedToEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        context = binding.applicationContext

        Log.d(
            "RuStoreBillingPlugin",
            "Trying to resolve Application from Context: ${context.javaClass.name}"
        )

        application = context as Application
        Rustore.RustoreBilling.setup(binding.binaryMessenger, client)

    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {

    }

    override fun onNewIntent(intent: Intent): Boolean {
        val client = FlutterRustoreBillingClient(application)
        client.onNewIntent(intent)
        return true
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {

    }

    override fun onDetachedFromActivityForConfigChanges() {

    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {

    }

    override fun onDetachedFromActivity() {
    }
}

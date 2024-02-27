package ru.rustore.flutter_rustore_billing

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.annotation.NonNull

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.PluginRegistry.NewIntentListener
import ru.rustore.flutter_rustore_billing.pigeons.Rustore

/** FlutterRustoreBillingPlugin */
class FlutterRustoreBillingPlugin : FlutterPlugin, ActivityAware {
    private lateinit var context: Context
    private lateinit var application: Application

    override fun onAttachedToEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        context = binding.applicationContext

        Log.d(
            "RuStoreBillingPlugin",
            "Trying to resolve Application from Context: ${context.javaClass.name}"
        )

        application = context as Application

        val client = FlutterRustoreBillingClient(application)
        Rustore.RustoreBilling.setup(binding.binaryMessenger, client)

//    activity = binding.applicationContext.startActivity()


    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {

    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        val client = FlutterRustoreBillingClient(application)
        var activity = binding.activity
        // TODO()
        binding.addOnNewIntentListener {
            it.let { intent -> client.onNewIntent(intent) }
        }
    }

    override fun onDetachedFromActivityForConfigChanges() {

    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {

    }

    override fun onDetachedFromActivity() {
    }
}

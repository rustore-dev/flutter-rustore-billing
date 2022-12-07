package ru.rustore.flutter_rustore_billing

import android.app.Application
import android.content.Context
import androidx.annotation.NonNull

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import ru.rustore.flutter_rustore_billing.pigeons.Rustore
import ru.rustore.flutter_rustore_billing.utils.Log

/** FlutterRustoreBillingPlugin */
class FlutterRustoreBillingPlugin: FlutterPlugin, ActivityAware {
  private lateinit var context: Context
  private lateinit var application: Application

  override fun onAttachedToEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    context = binding.applicationContext

    Log.d("Trying to resolve Application from Context: ${context.javaClass.name}")

    application = context as Application

    val client = FlutterRustoreBillingClient(application)
    Rustore.Client.setup(binding.binaryMessenger, client)
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {

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

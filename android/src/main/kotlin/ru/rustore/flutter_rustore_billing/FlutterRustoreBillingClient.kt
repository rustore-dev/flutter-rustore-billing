package ru.rustore.flutter_rustore_billing

import android.app.Application
import ru.rustore.flutter_rustore_billing.pigeons.Rustore
import ru.rustore.sdk.billingclient.RuStoreBillingClient
import ru.rustore.sdk.core.feature.model.FeatureAvailabilityResult
import ru.rustore.sdk.core.tasks.OnCompleteListener

class FlutterRustoreBillingClient(private val app: Application): Rustore.Client {
    override fun initialize(id: String, prefix: String, result: Rustore.Result<String>?) {
        RuStoreBillingClient.init(
            application = app,
            consoleApplicationId = id,
            deeplinkPrefix = prefix,
        )

        result?.success(null)
    }

    override fun available(result: Rustore.Result<Boolean>?) {
        RuStoreBillingClient.purchases.checkPurchasesAvailability().addOnCompleteListener(object : OnCompleteListener<FeatureAvailabilityResult> {
            override fun onFailure(throwable: Throwable) {
                result?.error(throwable)
            }

            override fun onSuccess(value: FeatureAvailabilityResult) {
                when (value) {
                    is FeatureAvailabilityResult.Available -> {
                        result?.success(true)
                    }
                    is FeatureAvailabilityResult.Unavailable -> {
                        result?.success(false)
                    }
                }
            }
        })
    }

    override fun products(
        ids: MutableList<String>,
        result: Rustore.Result<Rustore.ProductResponse>?
    ) {
        TODO("Not yet implemented")
    }

    override fun purchases(result: Rustore.Result<Rustore.PurchasesResponse>?) {
        TODO("Not yet implemented")
    }

    override fun purchase(id: String, result: Rustore.Result<Rustore.PaymentResult>?) {
        TODO("Not yet implemented")
    }

    override fun confirm(id: String, result: Rustore.Result<Rustore.ConfirmPurchaseResponse>?) {
        TODO("Not yet implemented")
    }
}
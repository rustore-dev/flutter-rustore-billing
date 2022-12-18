package ru.rustore.flutter_rustore_billing

import android.app.Application
import ru.rustore.flutter_rustore_billing.pigeons.Rustore
import ru.rustore.sdk.billingclient.RuStoreBillingClient
import ru.rustore.sdk.billingclient.model.product.Product
import ru.rustore.sdk.billingclient.model.product.ProductsResponse
import ru.rustore.sdk.core.feature.model.FeatureAvailabilityResult
import ru.rustore.sdk.core.tasks.OnCompleteListener

class FlutterRustoreBillingClient(private val app: Application): Rustore.Client {
    override fun initialize(id: String, prefix: String, result: Rustore.Result<String>?) {
        RuStoreBillingClient.init(
            application = app,
            consoleApplicationId = id,
            deeplinkPrefix = prefix,
            debugLogs = true
        )

        result?.success("")
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
        out: Rustore.Result<Rustore.ProductsResponse>?
    ) {
        RuStoreBillingClient.products.getProducts(productIds = ids.toList())
            .addOnCompleteListener(object : OnCompleteListener<ProductsResponse> {
                override fun onFailure(throwable: Throwable) {
                    out?.error(throwable)
                }
                override fun onSuccess(result: ProductsResponse) {

                    val response = Rustore.ProductsResponse.Builder()
                    response.setCode(result.code.toLong())
                    response.setErrorMessage(result.errorMessage)
                    response.setErrorDescription(result.errorDescription)
                    response.setTraceId(result.traceId)

                    var products = mutableListOf<Rustore.Product>()

                    for (item in result.products ?: listOf()) {
                        products.add(
                            Rustore.Product.Builder()
                                .setId(item.productId)
                                .build()
                        )
                    }

                    response.setProducts(products)

                    out?.success(response.build())
                }
            })
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

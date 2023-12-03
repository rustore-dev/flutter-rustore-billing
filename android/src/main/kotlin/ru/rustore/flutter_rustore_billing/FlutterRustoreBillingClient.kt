package ru.rustore.flutter_rustore_billing

import android.app.Application
import ru.rustore.flutter_rustore_billing.pigeons.ConfirmPurchaseResponse
import ru.rustore.flutter_rustore_billing.pigeons.DigitalShopGeneralError
import ru.rustore.flutter_rustore_billing.pigeons.ProductsResponse
import ru.rustore.flutter_rustore_billing.pigeons.PurchasesResponse
import ru.rustore.flutter_rustore_billing.pigeons.RustoreBilling
import ru.rustore.flutter_rustore_billing.pigeons.Subscription
import ru.rustore.flutter_rustore_billing.pigeons.Product as RustoreProduct
import ru.rustore.flutter_rustore_billing.pigeons.Purchase as RustorePurchase
import ru.rustore.flutter_rustore_billing.pigeons.PaymentResult as RustorePaymentResult
import ru.rustore.flutter_rustore_billing.pigeons.InvalidPurchase as RustoreInvalidPurchase
import ru.rustore.flutter_rustore_billing.pigeons.SuccessPurchase as RustoreSuccessPurchase
import ru.rustore.flutter_rustore_billing.pigeons.SubscriptionPeriod as RustoreSubscriptionPeriod
import ru.rustore.sdk.billingclient.RuStoreBillingClient
import ru.rustore.sdk.billingclient.RuStoreBillingClientFactory
import ru.rustore.sdk.billingclient.model.product.Product
import ru.rustore.sdk.billingclient.model.product.SubscriptionPeriod
import ru.rustore.sdk.billingclient.model.purchase.PaymentResult
import ru.rustore.sdk.billingclient.model.purchase.Purchase
import ru.rustore.sdk.core.config.SdkType
import ru.rustore.sdk.core.feature.model.FeatureAvailabilityResult
import ru.rustore.sdk.core.tasks.OnCompleteListener

class FlutterRustoreBillingClient(private val app: Application) : RustoreBilling {
    private lateinit var client: RuStoreBillingClient

    override fun initialize(id: String, prefix: String, debug: Boolean, callback: (Result<String>) -> Unit) {
        client = RuStoreBillingClientFactory.create(
            context = app,
            consoleApplicationId = id,
            deeplinkScheme = prefix,
            debugLogs = true,
            internalConfig = mapOf("type" to SdkType.FLUTTER)
        )

        callback(Result.success(""))
    }

    override fun available(callback: (Result<Boolean>) -> Unit) {
        client.purchases.checkPurchasesAvailability()
            .addOnCompleteListener(object : OnCompleteListener<FeatureAvailabilityResult> {
                override fun onFailure(throwable: Throwable) {
                    callback(Result.failure(throwable))
                }

                override fun onSuccess(value: FeatureAvailabilityResult) {
                    when (value) {
                        is FeatureAvailabilityResult.Available -> {
                            callback(Result.success(true))
                        }

                        is FeatureAvailabilityResult.Unavailable -> {
                            callback(Result.success(false))
                        }
                    }
                }
            })
    }

    override fun products(ids: List<String?>, callback: (Result<ProductsResponse>) -> Unit) {
        client.products.getProducts(productIds = ids as List<String>)
            .addOnCompleteListener(object : OnCompleteListener<List<Product>> {
                override fun onFailure(throwable: Throwable) {
                    callback(Result.failure(throwable))
                }

                override fun onSuccess(result: List<Product>) {
                    val products = mutableListOf<RustoreProduct>()

                    for (item in result) {
                        var subscription: Subscription? = null

                        if (item.subscription != null) {
                            subscription = Subscription(
                                subscriptionPeriod = period(item.subscription?.subscriptionPeriod),
                                freeTrialPeriod = period(item.subscription?.freeTrialPeriod),
                                gracePeriod = period(item.subscription?.gracePeriod),
                                introductoryPricePeriod = period(item.subscription?.introductoryPricePeriod),
                                introductoryPrice = item.subscription?.introductoryPrice,
                                introductoryPriceAmount = item.subscription?.introductoryPriceAmount
                            )
                        }

                        val product = RustoreProduct(
                            productId = item.productId,
                            productType = item.productType?.toString(),
                            productStatus = item.productStatus.toString(),
                            priceLabel = item.priceLabel,
                            price = item.price?.toLong(),
                            currency = item.currency,
                            language = item.language,
                            title = item.title,
                            description = item.description,
                            imageUrl = item.imageUrl.toString(),
                            promoImageUrl = item.promoImageUrl.toString(),
                            subscription = subscription,
                        )

                        products.add(product)
                    }

                    val errors = mutableListOf<DigitalShopGeneralError>()
                    val response = ProductsResponse(
                        code = 200,
                        errors = errors,
                        products = products,
                    )

                    callback(Result.success(response))
                }
            })
    }

    override fun purchase(id: String, callback: (Result<RustorePaymentResult>) -> Unit) {
        client.purchases.purchaseProduct(
            productId = id
        ).addOnCompleteListener(object : OnCompleteListener<PaymentResult> {
            override fun onFailure(throwable: Throwable) {
                callback(Result.failure(throwable))
            }

            override fun onSuccess(result: PaymentResult) {

                var invalidPurchase: RustoreInvalidPurchase? = null
                var successPurchase: RustoreSuccessPurchase? = null

                when (result) {
                    is PaymentResult.Cancelled -> {

                        callback(Result.failure(Throwable(message = result.toString())))
                        return
                    }

                    is PaymentResult.Failure -> {
                        invalidPurchase = RustoreInvalidPurchase(
                            purchaseId = result.purchaseId,
                            invoiceId = result.invoiceId,
                            orderId = result.orderId,
                            quantity = result.quantity?.toLong(),
                            productId = result.productId,
                            errorCode = result.errorCode?.toLong(),
                        )
                    }

                    is PaymentResult.Success -> {
                        successPurchase = RustoreSuccessPurchase(
                            purchaseId = result.purchaseId,
                            invoiceId = result.invoiceId,
                            orderId = result.orderId,
                            productId = result.productId,
                            subscriptionToken = result.subscriptionToken,
                        )
                    }

                    is PaymentResult.InvalidPaymentState -> {
                        callback(Result.failure(Throwable(message = result.toString())))
                        return
                    }
                }

                val response = RustorePaymentResult(
                    invalidPurchase = invalidPurchase,
                    successPurchase = successPurchase,
                )

                callback(Result.success(response))
            }
        })
    }

    override fun purchases(callback: (Result<PurchasesResponse>) -> Unit) {
        client.purchases.getPurchases()
            .addOnCompleteListener(object : OnCompleteListener<List<Purchase>> {
                override fun onFailure(throwable: Throwable) {
                    callback(Result.failure(throwable))
                }

                override fun onSuccess(result: List<Purchase>) {
                    val purchases = mutableListOf<RustorePurchase>()

                    for (item in result) {
                        val purchase = RustorePurchase(
                            purchaseId = item.purchaseId,
                            productId = item.productId,
                            description = item.description,
                            language = item.language,
                            purchaseTime = item.purchaseTime.toString(),
                            orderId = item.orderId,
                            amountLabel = item.amountLabel,
                            amount = item.amount?.toLong(),
                            currency = item.currency,
                            quantity = item.quantity?.toLong(),
                            purchaseState = item.purchaseState.toString(),
                            developerPayload = item.developerPayload,
                            invoiceId = item.invoiceId,
                            subscriptionToken = item.subscriptionToken,
                        )

                        purchases.add(purchase)
                    }

                    val errors = mutableListOf<DigitalShopGeneralError>()
                    val response = PurchasesResponse(
                        code = 200,
                        purchases = purchases,
                        errors = errors,
                    )

                    callback(Result.success(response))
                }
            })
    }

    override fun confirm(id: String, callback: (Result<ConfirmPurchaseResponse>) -> Unit) {
        client.purchases.confirmPurchase(purchaseId = id)
            .addOnSuccessListener {
                val response = ConfirmPurchaseResponse(
                    success = true
                )

                callback(Result.success(response))
            }
            .addOnFailureListener {
                callback(Result.failure(it))
            }
    }

    fun period(sub: SubscriptionPeriod?): RustoreSubscriptionPeriod {
        val period = RustoreSubscriptionPeriod(
            days = sub?.days?.toLong() ?: 0,
            months = sub?.months?.toLong() ?: 0,
            years = sub?.years?.toLong() ?: 0,
        )

        return period
    }
}

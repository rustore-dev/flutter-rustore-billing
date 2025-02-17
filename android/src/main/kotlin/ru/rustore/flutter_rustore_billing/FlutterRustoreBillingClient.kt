package ru.rustore.flutter_rustore_billing

import InvalidPurchase
import SuccessPurchase
import ConfirmPurchaseResponse
import Purchase
import PurchasesResponse
import RustoreBilling
import Subscription
import Product
import PurchaseAvailabilityResultFlutter
import ProductsResponse
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import ru.rustore.flutter_rustore_billing.utils.BillingClientThemeProviderImpl
import ru.rustore.flutter_rustore_billing.utils.PaymentLogger
import ru.rustore.sdk.billingclient.RuStoreBillingClient
import ru.rustore.sdk.billingclient.RuStoreBillingClientFactory
import ru.rustore.sdk.billingclient.utils.resolveForBilling
import ru.rustore.sdk.core.config.SdkType
import ru.rustore.sdk.core.exception.RuStoreException
import ru.rustore.sdk.core.feature.model.FeatureAvailabilityResult
import ru.rustore.sdk.billingclient.model.product.ProductSubscription
import ru.rustore.sdk.billingclient.model.product.SubscriptionPeriod
import PaymentResult as FlutterPaymentResult
import SubscriptionPeriod as SdkSubscriptionPeriod
import ru.rustore.sdk.billingclient.model.purchase.PaymentResult
import ru.rustore.sdk.billingclient.model.purchase.PurchaseAvailabilityResult
import ru.rustore.sdk.core.util.RuStoreUtils


class FlutterRustoreBillingClient(private val app: Application) : RustoreBilling {
    private lateinit var client: RuStoreBillingClient
    private var context: Context? = null

    companion object {
        private var allowNativeErrorHandling = true
    }

    override fun initialize(
        id: String,
        prefix: String,
        debugLogs: Boolean,
        callback: (Result<String>) -> Unit
    ) {
        callback(
            runCatching {
                client = RuStoreBillingClientFactory.create(
                    context = app,
                    consoleApplicationId = id,
                    deeplinkScheme = prefix,
                    debugLogs = debugLogs,
                    externalPaymentLoggerFactory = { PaymentLogger("RuStoreFlutterBillingPlugin") },
                    themeProvider = BillingClientThemeProviderImpl(app.applicationContext),
                    internalConfig = mapOf("type" to SdkType.FLUTTER)
                )
                ""
            }
        )
    }

    override fun available(callback: (Result<PurchaseAvailabilityResultFlutter>) -> Unit) {
        client.purchases.checkPurchasesAvailability()
            .addOnSuccessListener { value ->
                val result = when (value) {
                    is PurchaseAvailabilityResult.Available ->
                        PurchaseAvailabilityResultFlutter(isAvailable = true)

                    is PurchaseAvailabilityResult.Unknown ->
                        PurchaseAvailabilityResultFlutter(isUnknown = true)

                    is PurchaseAvailabilityResult.Unavailable -> {
                        PurchaseAvailabilityResultFlutter(unavailableCause = value.cause.message.toString())
                    }
                }
                callback(Result.success(result))
            }
            .addOnFailureListener { throwable ->
                callback(Result.failure(throwable))
            }
    }

    override fun products(ids: List<String?>, callback: (Result<ProductsResponse>) -> Unit) {
        client.products.getProducts(productIds = ids.filterNotNull())
            .addOnSuccessListener { products ->
                val response = ProductsResponse(
                    products = products.map {
                        Product(
                            productType = it.productType?.toString(),
                            productId = it.productId,
                            productStatus = it.productStatus.toString(),
                            priceLabel = it.priceLabel,
                            price = it.price?.toLong(),
                            currency = it.currency,
                            language = it.language,
                            title = it.title,
                            description = it.description,
                            imageUrl = it.imageUrl.toString(),
                            promoImageUrl = it.promoImageUrl.toString(),
                            subscription = it.subscription?.toSubscription()
                        )
                    },
                    errors = emptyList()
                )
                callback(Result.success(response))
            }
            .addOnFailureListener { throwable ->
                handleError(throwable)
                callback(Result.failure(throwable))
            }
    }

    override fun purchases(callback: (Result<PurchasesResponse>) -> Unit) {
        client.purchases.getPurchases()
            .addOnSuccessListener { purchases ->
                val response = PurchasesResponse(
                    code = 200,
                    purchases = purchases.map {
                        Purchase(
                            purchaseId = it.purchaseId,
                            productId = it.productId,
                            productType = it.productType.toString(),
                            language = it.language,
                            purchaseTime = it.purchaseTime?.toString(),
                            orderId = it.orderId,
                            amountLabel = it.amountLabel,
                            amount = it.amount?.toLong(),
                            currency = it.currency,
                            quantity = it.quantity?.toLong(),
                            purchaseState = it.purchaseState.toString(),
                            developerPayload = it.developerPayload,
                            invoiceId = it.invoiceId,
                            subscriptionToken = it.subscriptionToken
                        )
                    },
                    errors = emptyList()
                )
                callback(Result.success(response))
            }
            .addOnFailureListener { throwable ->
                handleError(throwable)
                callback(Result.failure(throwable))
            }
    }

    override fun purchase(
        id: String,
        developerPayload: String?,
        callback: (Result<FlutterPaymentResult>) -> Unit
    ) {
        client.purchases.purchaseProduct(productId = id, developerPayload = developerPayload)
            .addOnSuccessListener { result ->
                val paymentResult = when (result) {
                    is PaymentResult.Cancelled -> {
                        callback(Result.failure(Throwable(message = result.toString())))
                        return@addOnSuccessListener
                    }

                    is PaymentResult.Failure -> {
                        FlutterPaymentResult(
                            invalidPurchase = InvalidPurchase(
                                purchaseId = result.purchaseId,
                                invoiceId = result.invoiceId,
                                orderId = result.orderId,
                                quantity = result.quantity?.toLong(),
                                productId = result.productId,
                                errorCode = result.errorCode?.toLong(),
                                sandbox = result.sandbox
                            )
                        )
                    }

                    is PaymentResult.Success -> {
                        FlutterPaymentResult(
                            successPurchase = SuccessPurchase(
                                orderId = result.orderId,
                                purchaseId = result.purchaseId,
                                productId = result.productId,
                                invoiceId = result.invoiceId,
                                subscriptionToken = result.subscriptionToken,
                                sandbox = result.sandbox
                            )
                        )
                    }

                    is PaymentResult.InvalidPaymentState -> TODO()
                }
                callback(Result.success(paymentResult))
            }
            .addOnFailureListener { throwable ->
                handleError(throwable)
                callback(Result.failure(throwable))
            }
    }

    override fun confirm(id: String, callback: (Result<ConfirmPurchaseResponse>) -> Unit) {
        client.purchases.confirmPurchase(purchaseId = id)
            .addOnSuccessListener {
                callback(Result.success(ConfirmPurchaseResponse(true)))
            }
            .addOnFailureListener { error ->
                callback(Result.failure(error))
            }
    }

    override fun deletePurchase(purchaseId: String, callback: (Result<Unit>) -> Unit) {
        client.purchases.deletePurchase(purchaseId)
            .addOnSuccessListener {
                callback(Result.success(Unit))
            }
            .addOnFailureListener { error ->
                callback(Result.failure(error))
            }
    }

    override fun getAuthorizationStatus(callback: (Result<Boolean>) -> Unit) {
        client.userInfo.getAuthorizationStatus()
            .addOnSuccessListener { status ->
                callback(Result.success(status.authorized))
            }
            .addOnFailureListener { error ->
                callback(Result.failure(error))
            }
    }

    override fun purchaseInfo(id: String, callback: (Result<Purchase>) -> Unit) {
        client.purchases.getPurchaseInfo(id)
            .addOnSuccessListener { result ->
                val purchase = Purchase(
                    purchaseId = result.purchaseId,
                    productId = result.productId,
                    productType = result.productType.toString(),
                    language = result.language,
                    purchaseTime = result.purchaseTime.toString(),
                    orderId = result.orderId,
                    amountLabel = result.amountLabel,
                    amount = result.amount?.toLong(),
                    currency = result.currency,
                    quantity = result.quantity?.toLong(),
                    purchaseState = result.purchaseState?.toString(),
                    developerPayload = result.developerPayload,
                    invoiceId = result.invoiceId,
                    subscriptionToken = result.subscriptionToken
                )
                callback(Result.success(purchase))
            }
            .addOnFailureListener { throwable ->
                callback(Result.failure(throwable))
            }
    }

    override fun offNativeErrorHandling() {
        allowNativeErrorHandling = false
    }

    override fun isRustoreInstalled(): Boolean =
        context?.let { RuStoreUtils.isRuStoreInstalled(it) } ?: false


    fun onNewIntent(intent: Intent) {
        client.onNewIntent(intent)
    }

    fun setActivityContext(activity: Activity) {
        context = activity
    }

    fun resetActivityContext() {
        context = null
    }

    private fun handleError(throwable: Throwable) {
        if (allowNativeErrorHandling && throwable is RuStoreException) {
            context?.let { throwable.resolveForBilling(it) }
        }
    }

    private fun ProductSubscription.toSubscription(): Subscription {
        return Subscription(
            subscriptionPeriod = subscriptionPeriod?.toSdkSubscriptionPeriod(),
            freeTrialPeriod = freeTrialPeriod?.toSdkSubscriptionPeriod(),
            gracePeriod = gracePeriod?.toSdkSubscriptionPeriod(),
            introductoryPrice = introductoryPrice,
            introductoryPriceAmount = introductoryPriceAmount,
            introductoryPricePeriod = introductoryPricePeriod?.toSdkSubscriptionPeriod(),
        )
    }

    private fun SubscriptionPeriod.toSdkSubscriptionPeriod(): SdkSubscriptionPeriod {
        return SdkSubscriptionPeriod(
            years = years.toLong(),
            months = months.toLong(),
            days = days.toLong()
        )
    }
}

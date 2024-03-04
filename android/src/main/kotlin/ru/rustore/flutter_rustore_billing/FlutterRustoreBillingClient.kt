package ru.rustore.flutter_rustore_billing

import android.app.Application
import android.content.Intent
import ru.rustore.flutter_rustore_billing.pigeons.Rustore
import ru.rustore.flutter_rustore_billing.utils.BillingClientThemeProviderImpl
import ru.rustore.flutter_rustore_billing.utils.PaymentLogger
import ru.rustore.sdk.billingclient.RuStoreBillingClient
import ru.rustore.sdk.billingclient.RuStoreBillingClientFactory
import ru.rustore.sdk.billingclient.model.product.SubscriptionPeriod
import ru.rustore.sdk.billingclient.model.purchase.PaymentResult
import ru.rustore.sdk.billingclient.utils.resolveForBilling
import ru.rustore.sdk.core.config.SdkType
import ru.rustore.sdk.core.exception.RuStoreException
import ru.rustore.sdk.core.feature.model.FeatureAvailabilityResult


class FlutterRustoreBillingClient(private val app: Application) : Rustore.RustoreBilling {
    private lateinit var client: RuStoreBillingClient

    private var allowNativeErrorHandling: Boolean = false

    override fun initialize(
        id: String,
        prefix: String,
        debugLogs: Boolean,
        allowNativeErrorHandling: Boolean,
        result: Rustore.Result<String>?
    ) {
        client = RuStoreBillingClientFactory.create(
            context = app,
            consoleApplicationId = id,
            deeplinkScheme = prefix,
            debugLogs = debugLogs,
            externalPaymentLoggerFactory = { PaymentLogger("RuStoreFlutterBillingPlugin") },
            themeProvider = BillingClientThemeProviderImpl(app.applicationContext),
            internalConfig = mapOf("type" to SdkType.FLUTTER)
        )
        this.allowNativeErrorHandling = allowNativeErrorHandling
        result?.success("")
    }

    override fun available(result: Rustore.Result<Boolean>?) {
        client.purchases.checkPurchasesAvailability()
            .addOnFailureListener { throwable ->
                handleError(throwable)
                result?.error(throwable)
            }
            .addOnSuccessListener { value ->
                when (value) {
                    is FeatureAvailabilityResult.Available -> {
                        result?.success(true)
                    }

                    is FeatureAvailabilityResult.Unavailable -> {
                        result?.success(false)
                        value.cause.resolveForBilling(this.app.applicationContext)
                    }
                }
            }
    }

    override fun products(
        ids: MutableList<String>,
        out: Rustore.Result<Rustore.ProductsResponse>?
    ) {
        client.products.getProducts(productIds = ids.toList())
            .addOnFailureListener { throwable ->
                handleError(throwable)
                out?.error(throwable)
            }
            .addOnSuccessListener { result ->
                val response = Rustore.ProductsResponse.Builder()

                val products = mutableListOf<Rustore.Product>()

                for (item in result) {
                    val product = Rustore.Product.Builder()
                        .setProductId(item.productId)
                        .setProductType(item.productType?.toString())
                        .setProductStatus(item.productStatus.toString())
                        .setPriceLabel(item.priceLabel)
                        .setPrice(item.price?.toLong())
                        .setCurrency(item.currency)
                        .setLanguage(item.language)
                        .setTitle(item.title)
                        .setDescription(item.description)
                        .setImageUrl(item.imageUrl.toString())
                        .setPromoImageUrl(item.promoImageUrl.toString())


                    if (item.subscription != null) {
                        val subscription = Rustore.Subscription.Builder()

                        subscription.setSubscriptionPeriod(period(item.subscription?.subscriptionPeriod))
                        subscription.setFreeTrialPeriod(period(item.subscription?.freeTrialPeriod))
                        subscription.setGracePeriod(period(item.subscription?.gracePeriod))
                        subscription.setIntroductoryPricePeriod(period(item.subscription?.introductoryPricePeriod))
                        subscription.setIntroductoryPrice(item.subscription?.introductoryPrice)
                        subscription.setIntroductoryPriceAmount(item.subscription?.introductoryPriceAmount)

                        product.setSubscription(subscription.build())
                    }

                    products.add(product.build())
                }

                val errors = mutableListOf<Rustore.DigitalShopGeneralError>()

                response.setProducts(products)
                response.setErrors(errors)
                out?.success(response.build())
            }
    }

    override fun purchase(
        id: String,
        developerPayload: String?,
        out: Rustore.Result<Rustore.PaymentResult>?
    ) {
        client.purchases.purchaseProduct(
            productId = id,
            developerPayload = developerPayload
        )
            .addOnFailureListener { throwable ->
                out?.error(throwable)
            }
            .addOnSuccessListener { result ->
                val response = Rustore.PaymentResult.Builder()

                when (result) {
                    is PaymentResult.Cancelled -> {
                        out?.error(Throwable(message = result.toString()))
                        return@addOnSuccessListener
                    }

                    is PaymentResult.Failure -> {
                        val purchase = Rustore.InvalidPurchase.Builder()
                        purchase.setPurchaseId(result.purchaseId)
                        purchase.setInvoiceId(result.invoiceId)
                        purchase.setOrderId(result.orderId)
                        purchase.setQuantity(result.quantity?.toLong())
                        purchase.setProductId(result.productId)
                        purchase.setErrorCode(result.errorCode?.toLong())

                        response.setInvalidPurchase(purchase.build())
                    }

                    is PaymentResult.Success -> {
                        val purchase = Rustore.SuccessPurchase.Builder()
                        purchase.setOrderId(result.orderId)
                        purchase.setPurchaseId(result.purchaseId)
                        purchase.setProductId(result.productId)
                        purchase.setInvoiceId(result.invoiceId)
                        purchase.setSubscriptionToken(result.subscriptionToken)

                        response.setSuccessPurchase(purchase.build())
                    }

                    is PaymentResult.InvalidPaymentState -> TODO()
                }
                out?.success(response.build())
            }
    }

    override fun purchases(out: Rustore.Result<Rustore.PurchasesResponse>?) {
        client.purchases.getPurchases()
            .addOnFailureListener { throwable ->
                handleError(throwable)
                out?.error(throwable)
            }
            .addOnSuccessListener { result ->
                val response = Rustore.PurchasesResponse.Builder()
                response.setCode(200)

                val purchases = mutableListOf<Rustore.Purchase>()

                for (item in result) {
                    val purchase = Rustore.Purchase.Builder()
                        .setPurchaseId(item.purchaseId)
                        .setProductId(item.productId)
                        .setDescription(item.description)
                        .setLanguage(item.language)
                        .setPurchaseTime(item.purchaseTime.toString())
                        .setOrderId(item.orderId)
                        .setAmountLabel(item.amountLabel)
                        .setAmount(item.amount?.toLong())
                        .setCurrency(item.currency)
                        .setQuantity(item.quantity?.toLong())
                        .setPurchaseState(item.purchaseState.toString())
                        .setDeveloperPayload(item.developerPayload)
                        .setInvoiceId(item.invoiceId)
                        .setSubscriptionToken(item.subscriptionToken)

                    purchases.add(purchase.build())
                }

                val errors = mutableListOf<Rustore.DigitalShopGeneralError>()

                response.setErrors(errors)
                response.setPurchases(purchases)

                out?.success(response.build())
            }
    }

    override fun purchaseInfo(
        id: String,
        out: Rustore.Result<Rustore.Purchase>?
    ) {
        client.purchases.getPurchaseInfo(id)
            .addOnSuccessListener { result ->
                val purchase = Rustore.Purchase.Builder()
                    .setPurchaseId(result.purchaseId)
                    .setProductId(result.productId)
                    .setDescription(result.description)
                    .setLanguage(result.language)
                    .setPurchaseTime(result.purchaseTime.toString())
                    .setOrderId(result.orderId)
                    .setAmountLabel(result.amountLabel)
                    .setAmount(result.amount?.toLong())
                    .setCurrency(result.currency)
                    .setQuantity(result.quantity?.toLong())
                    .setPurchaseState(result.purchaseState.toString())
                    .setDeveloperPayload(result.developerPayload)
                    .setInvoiceId(result.invoiceId)
                    .setSubscriptionToken(result.subscriptionToken)

                out?.success(purchase.build())
            }

            .addOnFailureListener { throwable ->
                handleError(throwable)
                out?.error(throwable)
            }
    }

    override fun confirm(id: String, out: Rustore.Result<Rustore.ConfirmPurchaseResponse>?) {
        client.purchases.confirmPurchase(purchaseId = id)
            .addOnSuccessListener {
                val response = Rustore.ConfirmPurchaseResponse.Builder()
                response.setSuccess(true)

                out?.success(response.build())
            }
            .addOnFailureListener { throwable ->
                handleError(throwable)
                out?.error(throwable)
            }
    }

    fun onNewIntent(intent: Intent) {
        client.onNewIntent(intent)
    }

    private fun handleError(throwable: Throwable) {
        if (allowNativeErrorHandling && throwable is RuStoreException) {
            throwable.resolveForBilling(this.app.baseContext)
        }
    }

    private fun period(sub: SubscriptionPeriod?): Rustore.SubscriptionPeriod {
        val period = Rustore.SubscriptionPeriod.Builder()
        period.setDays(sub?.days?.toLong() ?: 0)
        period.setMonths(sub?.months?.toLong() ?: 0)
        period.setYears(sub?.years?.toLong() ?: 0)

        return period.build()
    }
}

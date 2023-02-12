package ru.rustore.flutter_rustore_billing

import android.app.Application
import ru.rustore.flutter_rustore_billing.pigeons.Rustore
import ru.rustore.sdk.billingclient.RuStoreBillingClient
import ru.rustore.sdk.billingclient.model.product.ProductsResponse
import ru.rustore.sdk.billingclient.model.product.SubscriptionPeriod
import ru.rustore.sdk.billingclient.model.purchase.PaymentResult
import ru.rustore.sdk.billingclient.model.purchase.response.ConfirmPurchaseResponse
import ru.rustore.sdk.billingclient.model.purchase.response.PurchasesResponse
import ru.rustore.sdk.core.config.SdkType
import ru.rustore.sdk.core.feature.model.FeatureAvailabilityResult
import ru.rustore.sdk.core.tasks.OnCompleteListener

class FlutterRustoreBillingClient(private val app: Application) : Rustore.Client {
    override fun initialize(id: String, prefix: String, result: Rustore.Result<String>?) {
        RuStoreBillingClient.init(
            application = app,
            consoleApplicationId = id,
            deeplinkScheme = prefix,
            debugLogs = true,
            internalConfig = mapOf("type" to SdkType.FLUTTER)
        )

        result?.success("")
    }

    override fun available(result: Rustore.Result<Boolean>?) {
        RuStoreBillingClient.purchases.checkPurchasesAvailability()
            .addOnCompleteListener(object : OnCompleteListener<FeatureAvailabilityResult> {
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

                    val products = mutableListOf<Rustore.Product>()

                    for (item in result.products ?: listOf()) {
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

                    for (item in result.errors ?: listOf()) {
                        val error = Rustore.DigitalShopGeneralError.Builder()

                        error.setCode(item.code?.toLong())
                        error.setName(item.name)
                        error.setDescription(item.description)

                        errors.add(error.build())
                    }

                    response.setProducts(products)
                    response.setErrors(errors)

                    out?.success(response.build())
                }
            })
    }

    override fun purchase(id: String, out: Rustore.Result<Rustore.PaymentResult>?) {
        RuStoreBillingClient.purchases.purchaseProduct(
            productId = id
        ).addOnCompleteListener(object : OnCompleteListener<PaymentResult> {
            override fun onFailure(throwable: Throwable) {
                out?.error(throwable)
            }

            override fun onSuccess(result: PaymentResult) {
                val response = Rustore.PaymentResult.Builder()

                when (result) {
                    is PaymentResult.InvoiceResult -> {
                        val invoice = Rustore.SuccessInvoice.Builder()
                        invoice.setInvoiceId(result.invoiceId)
                        invoice.setFinishCode(result.finishCode.toString())

                        response.setSuccessInvoice(invoice.build())
                    }

                    is PaymentResult.InvalidInvoice -> {
                        val invoice = Rustore.InvalidInvoice.Builder()
                        invoice.setInvoiceId(result.invoiceId)

                        response.setInvalidInvoice(invoice.build())
                    }

                    is PaymentResult.PurchaseResult -> {
                        val purchase = Rustore.SuccessPurchase.Builder()
                        purchase.setFinishCode(result.finishCode.toString())
                        purchase.setOrderId(result.orderId)
                        purchase.setPurchaseId(result.purchaseId)
                        purchase.setProductId(result.productId)

                        response.setSuccessPurchase(purchase.build())
                    }

                    is PaymentResult.InvalidPurchase -> {
                        val purchase = Rustore.InvalidPurchase.Builder()
                        purchase.setPurchaseId(result.purchaseId)
                        purchase.setInvoiceId(result.invoiceId)
                        purchase.setOrderId(result.orderId)
                        purchase.setQuantity(result.quantity?.toLong())
                        purchase.setProductId(result.productId)
                        purchase.setErrorCode(result.errorCode?.toLong())

                        response.setInvalidPurchase(purchase.build())
                    }

                    is PaymentResult.InvalidPaymentState -> {
                        out?.error(Throwable(message = result.toString()))
                        return
                    }
                }

                out?.success(response.build())
            }
        })
    }

    override fun purchases(out: Rustore.Result<Rustore.PurchasesResponse>?) {
        RuStoreBillingClient.purchases.getPurchases()
            .addOnCompleteListener(object : OnCompleteListener<PurchasesResponse> {
                override fun onFailure(throwable: Throwable) {
                    out?.error(throwable)
                }

                override fun onSuccess(result: PurchasesResponse) {
                    val response = Rustore.PurchasesResponse.Builder()
                    response.setCode(result.code.toLong())
                    response.setErrorMessage(result.errorMessage)
                    response.setErrorDescription(result.errorDescription)

                    val purchases = mutableListOf<Rustore.Purchase>()

                    for (item in result.purchases ?: listOf()) {
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

                        purchases.add(purchase.build())
                    }

                    val errors = mutableListOf<Rustore.DigitalShopGeneralError>()

                    for (item in result.errors ?: listOf()) {
                        val error = Rustore.DigitalShopGeneralError.Builder()

                        error.setCode(item.code?.toLong())
                        error.setName(item.name)
                        error.setDescription(item.description)

                        errors.add(error.build())
                    }

                    response.setErrors(errors)
                    response.setPurchases(purchases)

                    out?.success(response.build())
                }
            })
    }

    override fun confirm(id: String, out: Rustore.Result<Rustore.ConfirmPurchaseResponse>?) {
        RuStoreBillingClient.purchases.confirmPurchase(purchaseId = id)
            .addOnCompleteListener(object : OnCompleteListener<ConfirmPurchaseResponse> {
                override fun onFailure(throwable: Throwable) {
                    out?.error(throwable)
                }

                override fun onSuccess(result: ConfirmPurchaseResponse) {
                    val response = Rustore.ConfirmPurchaseResponse.Builder()
                    response.setCode(result.code.toLong())
                    response.setErrorMessage(result.errorMessage)
                    response.setErrorDescription(result.errorDescription)

                    val errors = mutableListOf<Rustore.DigitalShopGeneralError>()

                    for (item in result.errors ?: listOf()) {
                        val error = Rustore.DigitalShopGeneralError.Builder()

                        error.setCode(item.code?.toLong())
                        error.setName(item.name)
                        error.setDescription(item.description)

                        errors.add(error.build())
                    }

                    response.setErrors(errors)

                    out?.success(response.build())
                }
            })

    }

    fun period(sub: SubscriptionPeriod?): Rustore.SubscriptionPeriod {
        val period = Rustore.SubscriptionPeriod.Builder()
        period.setDays(sub?.days?.toLong() ?: 0)
        period.setMonths(sub?.months?.toLong() ?: 0)
        period.setYears(sub?.years?.toLong() ?: 0)

        return period.build()
    }
}

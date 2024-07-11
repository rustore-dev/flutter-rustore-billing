// Autogenerated from Pigeon (v17.3.0), do not edit directly.
// See also: https://pub.dev/packages/pigeon


import android.util.Log
import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MessageCodec
import io.flutter.plugin.common.StandardMessageCodec
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

private fun wrapResult(result: Any?): List<Any?> {
  return listOf(result)
}

private fun wrapError(exception: Throwable): List<Any?> {
  if (exception is FlutterError) {
    return listOf(
      exception.code,
      exception.message,
      exception.details
    )
  } else {
    return listOf(
      exception.javaClass.simpleName,
      exception.toString(),
      "Cause: " + exception.cause + ", Stacktrace: " + Log.getStackTraceString(exception)
    )
  }
}

/**
 * Error class for passing custom error details to Flutter via a thrown PlatformException.
 * @property code The error code.
 * @property message The error message.
 * @property details The error details. Must be a datatype supported by the api codec.
 */
class FlutterError (
  val code: String,
  override val message: String? = null,
  val details: Any? = null
) : Throwable()

/** Generated class from Pigeon that represents data sent in messages. */
data class SubscriptionPeriod (
  val years: Long,
  val months: Long,
  val days: Long

) {
  companion object {
    @Suppress("UNCHECKED_CAST")
    fun fromList(list: List<Any?>): SubscriptionPeriod {
      val years = list[0].let { if (it is Int) it.toLong() else it as Long }
      val months = list[1].let { if (it is Int) it.toLong() else it as Long }
      val days = list[2].let { if (it is Int) it.toLong() else it as Long }
      return SubscriptionPeriod(years, months, days)
    }
  }
  fun toList(): List<Any?> {
    return listOf<Any?>(
      years,
      months,
      days,
    )
  }
}

/** Generated class from Pigeon that represents data sent in messages. */
data class Subscription (
  val subscriptionPeriod: SubscriptionPeriod? = null,
  val freeTrialPeriod: SubscriptionPeriod? = null,
  val gracePeriod: SubscriptionPeriod? = null,
  val introductoryPrice: String? = null,
  val introductoryPriceAmount: String? = null,
  val introductoryPricePeriod: SubscriptionPeriod? = null

) {
  companion object {
    @Suppress("UNCHECKED_CAST")
    fun fromList(list: List<Any?>): Subscription {
      val subscriptionPeriod: SubscriptionPeriod? = (list[0] as List<Any?>?)?.let {
        SubscriptionPeriod.fromList(it)
      }
      val freeTrialPeriod: SubscriptionPeriod? = (list[1] as List<Any?>?)?.let {
        SubscriptionPeriod.fromList(it)
      }
      val gracePeriod: SubscriptionPeriod? = (list[2] as List<Any?>?)?.let {
        SubscriptionPeriod.fromList(it)
      }
      val introductoryPrice = list[3] as String?
      val introductoryPriceAmount = list[4] as String?
      val introductoryPricePeriod: SubscriptionPeriod? = (list[5] as List<Any?>?)?.let {
        SubscriptionPeriod.fromList(it)
      }
      return Subscription(subscriptionPeriod, freeTrialPeriod, gracePeriod, introductoryPrice, introductoryPriceAmount, introductoryPricePeriod)
    }
  }
  fun toList(): List<Any?> {
    return listOf<Any?>(
      subscriptionPeriod?.toList(),
      freeTrialPeriod?.toList(),
      gracePeriod?.toList(),
      introductoryPrice,
      introductoryPriceAmount,
      introductoryPricePeriod?.toList(),
    )
  }
}

/** Generated class from Pigeon that represents data sent in messages. */
data class Product (
  val productId: String,
  val productType: String? = null,
  val productStatus: String,
  val priceLabel: String? = null,
  val price: Long? = null,
  val currency: String? = null,
  val language: String? = null,
  val title: String? = null,
  val description: String? = null,
  val imageUrl: String? = null,
  val promoImageUrl: String? = null,
  val subscription: Subscription? = null

) {
  companion object {
    @Suppress("UNCHECKED_CAST")
    fun fromList(list: List<Any?>): Product {
      val productId = list[0] as String
      val productType = list[1] as String?
      val productStatus = list[2] as String
      val priceLabel = list[3] as String?
      val price = list[4].let { if (it is Int) it.toLong() else it as Long? }
      val currency = list[5] as String?
      val language = list[6] as String?
      val title = list[7] as String?
      val description = list[8] as String?
      val imageUrl = list[9] as String?
      val promoImageUrl = list[10] as String?
      val subscription: Subscription? = (list[11] as List<Any?>?)?.let {
        Subscription.fromList(it)
      }
      return Product(productId, productType, productStatus, priceLabel, price, currency, language, title, description, imageUrl, promoImageUrl, subscription)
    }
  }
  fun toList(): List<Any?> {
    return listOf<Any?>(
      productId,
      productType,
      productStatus,
      priceLabel,
      price,
      currency,
      language,
      title,
      description,
      imageUrl,
      promoImageUrl,
      subscription?.toList(),
    )
  }
}

/** Generated class from Pigeon that represents data sent in messages. */
data class ProductsResponse (
  val code: Long? = null,
  val errorMessage: String? = null,
  val errorDescription: String? = null,
  val traceId: String? = null,
  val products: List<Product?>,
  val errors: List<DigitalShopGeneralError?>

) {
  companion object {
    @Suppress("UNCHECKED_CAST")
    fun fromList(list: List<Any?>): ProductsResponse {
      val code = list[0].let { if (it is Int) it.toLong() else it as Long? }
      val errorMessage = list[1] as String?
      val errorDescription = list[2] as String?
      val traceId = list[3] as String?
      val products = list[4] as List<Product?>
      val errors = list[5] as List<DigitalShopGeneralError?>
      return ProductsResponse(code, errorMessage, errorDescription, traceId, products, errors)
    }
  }
  fun toList(): List<Any?> {
    return listOf<Any?>(
      code,
      errorMessage,
      errorDescription,
      traceId,
      products,
      errors,
    )
  }
}

/** Generated class from Pigeon that represents data sent in messages. */
data class Purchase (
  val purchaseId: String? = null,
  val productId: String? = null,
  val productType: String? = null,
  val language: String? = null,
  val purchaseTime: String? = null,
  val orderId: String? = null,
  val amountLabel: String? = null,
  val amount: Long? = null,
  val currency: String? = null,
  val quantity: Long? = null,
  val purchaseState: String? = null,
  val developerPayload: String? = null,
  val invoiceId: String? = null,
  val subscriptionToken: String? = null

) {
  companion object {
    @Suppress("UNCHECKED_CAST")
    fun fromList(list: List<Any?>): Purchase {
      val purchaseId = list[0] as String?
      val productId = list[1] as String?
      val productType = list[2] as String?
      val language = list[3] as String?
      val purchaseTime = list[4] as String?
      val orderId = list[5] as String?
      val amountLabel = list[6] as String?
      val amount = list[7].let { if (it is Int) it.toLong() else it as Long? }
      val currency = list[8] as String?
      val quantity = list[9].let { if (it is Int) it.toLong() else it as Long? }
      val purchaseState = list[10] as String?
      val developerPayload = list[11] as String?
      val invoiceId = list[12] as String?
      val subscriptionToken = list[13] as String?
      return Purchase(purchaseId, productId, productType, language, purchaseTime, orderId, amountLabel, amount, currency, quantity, purchaseState, developerPayload, invoiceId, subscriptionToken)
    }
  }
  fun toList(): List<Any?> {
    return listOf<Any?>(
      purchaseId,
      productId,
      productType,
      language,
      purchaseTime,
      orderId,
      amountLabel,
      amount,
      currency,
      quantity,
      purchaseState,
      developerPayload,
      invoiceId,
      subscriptionToken,
    )
  }
}

/** Generated class from Pigeon that represents data sent in messages. */
data class PurchasesResponse (
  val code: Long,
  val errorMessage: String? = null,
  val errorDescription: String? = null,
  val traceId: String? = null,
  val purchases: List<Purchase?>,
  val errors: List<DigitalShopGeneralError?>

) {
  companion object {
    @Suppress("UNCHECKED_CAST")
    fun fromList(list: List<Any?>): PurchasesResponse {
      val code = list[0].let { if (it is Int) it.toLong() else it as Long }
      val errorMessage = list[1] as String?
      val errorDescription = list[2] as String?
      val traceId = list[3] as String?
      val purchases = list[4] as List<Purchase?>
      val errors = list[5] as List<DigitalShopGeneralError?>
      return PurchasesResponse(code, errorMessage, errorDescription, traceId, purchases, errors)
    }
  }
  fun toList(): List<Any?> {
    return listOf<Any?>(
      code,
      errorMessage,
      errorDescription,
      traceId,
      purchases,
      errors,
    )
  }
}

/** Generated class from Pigeon that represents data sent in messages. */
data class ConfirmPurchaseResponse (
  val success: Boolean,
  val errorMessage: String? = null

) {
  companion object {
    @Suppress("UNCHECKED_CAST")
    fun fromList(list: List<Any?>): ConfirmPurchaseResponse {
      val success = list[0] as Boolean
      val errorMessage = list[1] as String?
      return ConfirmPurchaseResponse(success, errorMessage)
    }
  }
  fun toList(): List<Any?> {
    return listOf<Any?>(
      success,
      errorMessage,
    )
  }
}

/** Generated class from Pigeon that represents data sent in messages. */
data class PaymentResult (
  val successInvoice: SuccessInvoice? = null,
  val invalidInvoice: InvalidInvoice? = null,
  val successPurchase: SuccessPurchase? = null,
  val invalidPurchase: InvalidPurchase? = null

) {
  companion object {
    @Suppress("UNCHECKED_CAST")
    fun fromList(list: List<Any?>): PaymentResult {
      val successInvoice: SuccessInvoice? = (list[0] as List<Any?>?)?.let {
        SuccessInvoice.fromList(it)
      }
      val invalidInvoice: InvalidInvoice? = (list[1] as List<Any?>?)?.let {
        InvalidInvoice.fromList(it)
      }
      val successPurchase: SuccessPurchase? = (list[2] as List<Any?>?)?.let {
        SuccessPurchase.fromList(it)
      }
      val invalidPurchase: InvalidPurchase? = (list[3] as List<Any?>?)?.let {
        InvalidPurchase.fromList(it)
      }
      return PaymentResult(successInvoice, invalidInvoice, successPurchase, invalidPurchase)
    }
  }
  fun toList(): List<Any?> {
    return listOf<Any?>(
      successInvoice?.toList(),
      invalidInvoice?.toList(),
      successPurchase?.toList(),
      invalidPurchase?.toList(),
    )
  }
}

/** Generated class from Pigeon that represents data sent in messages. */
data class SuccessInvoice (
  val invoiceId: String,
  val finishCode: String

) {
  companion object {
    @Suppress("UNCHECKED_CAST")
    fun fromList(list: List<Any?>): SuccessInvoice {
      val invoiceId = list[0] as String
      val finishCode = list[1] as String
      return SuccessInvoice(invoiceId, finishCode)
    }
  }
  fun toList(): List<Any?> {
    return listOf<Any?>(
      invoiceId,
      finishCode,
    )
  }
}

/** Generated class from Pigeon that represents data sent in messages. */
data class InvalidInvoice (
  val invoiceId: String? = null

) {
  companion object {
    @Suppress("UNCHECKED_CAST")
    fun fromList(list: List<Any?>): InvalidInvoice {
      val invoiceId = list[0] as String?
      return InvalidInvoice(invoiceId)
    }
  }
  fun toList(): List<Any?> {
    return listOf<Any?>(
      invoiceId,
    )
  }
}

/** Generated class from Pigeon that represents data sent in messages. */
data class SuccessPurchase (
  val finishCode: String? = null,
  val orderId: String? = null,
  val purchaseId: String,
  val productId: String,
  val invoiceId: String? = null,
  val subscriptionToken: String? = null,
  val sandbox: Boolean? = null

) {
  companion object {
    @Suppress("UNCHECKED_CAST")
    fun fromList(list: List<Any?>): SuccessPurchase {
      val finishCode = list[0] as String?
      val orderId = list[1] as String?
      val purchaseId = list[2] as String
      val productId = list[3] as String
      val invoiceId = list[4] as String?
      val subscriptionToken = list[5] as String?
      val sandbox = list[6] as Boolean?
      return SuccessPurchase(finishCode, orderId, purchaseId, productId, invoiceId, subscriptionToken, sandbox)
    }
  }
  fun toList(): List<Any?> {
    return listOf<Any?>(
      finishCode,
      orderId,
      purchaseId,
      productId,
      invoiceId,
      subscriptionToken,
      sandbox,
    )
  }
}

/** Generated class from Pigeon that represents data sent in messages. */
data class InvalidPurchase (
  val purchaseId: String? = null,
  val invoiceId: String? = null,
  val orderId: String? = null,
  val quantity: Long? = null,
  val productId: String? = null,
  val errorCode: Long? = null,
  val sandbox: Boolean? = null

) {
  companion object {
    @Suppress("UNCHECKED_CAST")
    fun fromList(list: List<Any?>): InvalidPurchase {
      val purchaseId = list[0] as String?
      val invoiceId = list[1] as String?
      val orderId = list[2] as String?
      val quantity = list[3].let { if (it is Int) it.toLong() else it as Long? }
      val productId = list[4] as String?
      val errorCode = list[5].let { if (it is Int) it.toLong() else it as Long? }
      val sandbox = list[6] as Boolean?
      return InvalidPurchase(purchaseId, invoiceId, orderId, quantity, productId, errorCode, sandbox)
    }
  }
  fun toList(): List<Any?> {
    return listOf<Any?>(
      purchaseId,
      invoiceId,
      orderId,
      quantity,
      productId,
      errorCode,
      sandbox,
    )
  }
}

/** Generated class from Pigeon that represents data sent in messages. */
data class DigitalShopGeneralError (
  val name: String? = null,
  val code: Long? = null,
  val description: String? = null

) {
  companion object {
    @Suppress("UNCHECKED_CAST")
    fun fromList(list: List<Any?>): DigitalShopGeneralError {
      val name = list[0] as String?
      val code = list[1].let { if (it is Int) it.toLong() else it as Long? }
      val description = list[2] as String?
      return DigitalShopGeneralError(name, code, description)
    }
  }
  fun toList(): List<Any?> {
    return listOf<Any?>(
      name,
      code,
      description,
    )
  }
}

@Suppress("UNCHECKED_CAST")
private object RustoreBillingCodec : StandardMessageCodec() {
  override fun readValueOfType(type: Byte, buffer: ByteBuffer): Any? {
    return when (type) {
      128.toByte() -> {
        return (readValue(buffer) as? List<Any?>)?.let {
          ConfirmPurchaseResponse.fromList(it)
        }
      }
      129.toByte() -> {
        return (readValue(buffer) as? List<Any?>)?.let {
          DigitalShopGeneralError.fromList(it)
        }
      }
      130.toByte() -> {
        return (readValue(buffer) as? List<Any?>)?.let {
          InvalidInvoice.fromList(it)
        }
      }
      131.toByte() -> {
        return (readValue(buffer) as? List<Any?>)?.let {
          InvalidPurchase.fromList(it)
        }
      }
      132.toByte() -> {
        return (readValue(buffer) as? List<Any?>)?.let {
          PaymentResult.fromList(it)
        }
      }
      133.toByte() -> {
        return (readValue(buffer) as? List<Any?>)?.let {
          Product.fromList(it)
        }
      }
      134.toByte() -> {
        return (readValue(buffer) as? List<Any?>)?.let {
          ProductsResponse.fromList(it)
        }
      }
      135.toByte() -> {
        return (readValue(buffer) as? List<Any?>)?.let {
          Purchase.fromList(it)
        }
      }
      136.toByte() -> {
        return (readValue(buffer) as? List<Any?>)?.let {
          PurchasesResponse.fromList(it)
        }
      }
      137.toByte() -> {
        return (readValue(buffer) as? List<Any?>)?.let {
          Subscription.fromList(it)
        }
      }
      138.toByte() -> {
        return (readValue(buffer) as? List<Any?>)?.let {
          SubscriptionPeriod.fromList(it)
        }
      }
      139.toByte() -> {
        return (readValue(buffer) as? List<Any?>)?.let {
          SuccessInvoice.fromList(it)
        }
      }
      140.toByte() -> {
        return (readValue(buffer) as? List<Any?>)?.let {
          SuccessPurchase.fromList(it)
        }
      }
      else -> super.readValueOfType(type, buffer)
    }
  }
  override fun writeValue(stream: ByteArrayOutputStream, value: Any?)   {
    when (value) {
      is ConfirmPurchaseResponse -> {
        stream.write(128)
        writeValue(stream, value.toList())
      }
      is DigitalShopGeneralError -> {
        stream.write(129)
        writeValue(stream, value.toList())
      }
      is InvalidInvoice -> {
        stream.write(130)
        writeValue(stream, value.toList())
      }
      is InvalidPurchase -> {
        stream.write(131)
        writeValue(stream, value.toList())
      }
      is PaymentResult -> {
        stream.write(132)
        writeValue(stream, value.toList())
      }
      is Product -> {
        stream.write(133)
        writeValue(stream, value.toList())
      }
      is ProductsResponse -> {
        stream.write(134)
        writeValue(stream, value.toList())
      }
      is Purchase -> {
        stream.write(135)
        writeValue(stream, value.toList())
      }
      is PurchasesResponse -> {
        stream.write(136)
        writeValue(stream, value.toList())
      }
      is Subscription -> {
        stream.write(137)
        writeValue(stream, value.toList())
      }
      is SubscriptionPeriod -> {
        stream.write(138)
        writeValue(stream, value.toList())
      }
      is SuccessInvoice -> {
        stream.write(139)
        writeValue(stream, value.toList())
      }
      is SuccessPurchase -> {
        stream.write(140)
        writeValue(stream, value.toList())
      }
      else -> super.writeValue(stream, value)
    }
  }
}

/** Generated interface from Pigeon that represents a handler of messages from Flutter. */
interface RustoreBilling {
  fun initialize(id: String, prefix: String, debugLogs: Boolean, callback: (Result<String>) -> Unit)
  fun available(callback: (Result<Boolean>) -> Unit)
  fun products(ids: List<String?>, callback: (Result<ProductsResponse>) -> Unit)
  fun purchases(callback: (Result<PurchasesResponse>) -> Unit)
  fun purchase(id: String, developerPayload: String?, callback: (Result<PaymentResult>) -> Unit)
  fun purchaseInfo(id: String, callback: (Result<Purchase>) -> Unit)
  fun confirm(id: String, callback: (Result<ConfirmPurchaseResponse>) -> Unit)

  companion object {
    /** The codec used by RustoreBilling. */
    val codec: MessageCodec<Any?> by lazy {
      RustoreBillingCodec
    }
    /** Sets up an instance of `RustoreBilling` to handle messages through the `binaryMessenger`. */
    @Suppress("UNCHECKED_CAST")
    fun setUp(binaryMessenger: BinaryMessenger, api: RustoreBilling?) {
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.flutter_rustore_billing.RustoreBilling.initialize", codec)
        if (api != null) {
          channel.setMessageHandler { message, reply ->
            val args = message as List<Any?>
            val idArg = args[0] as String
            val prefixArg = args[1] as String
            val debugLogsArg = args[2] as Boolean
            api.initialize(idArg, prefixArg, debugLogsArg) { result: Result<String> ->
              val error = result.exceptionOrNull()
              if (error != null) {
                reply.reply(wrapError(error))
              } else {
                val data = result.getOrNull()
                reply.reply(wrapResult(data))
              }
            }
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.flutter_rustore_billing.RustoreBilling.available", codec)
        if (api != null) {
          channel.setMessageHandler { _, reply ->
            api.available() { result: Result<Boolean> ->
              val error = result.exceptionOrNull()
              if (error != null) {
                reply.reply(wrapError(error))
              } else {
                val data = result.getOrNull()
                reply.reply(wrapResult(data))
              }
            }
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.flutter_rustore_billing.RustoreBilling.products", codec)
        if (api != null) {
          channel.setMessageHandler { message, reply ->
            val args = message as List<Any?>
            val idsArg = args[0] as List<String?>
            api.products(idsArg) { result: Result<ProductsResponse> ->
              val error = result.exceptionOrNull()
              if (error != null) {
                reply.reply(wrapError(error))
              } else {
                val data = result.getOrNull()
                reply.reply(wrapResult(data))
              }
            }
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.flutter_rustore_billing.RustoreBilling.purchases", codec)
        if (api != null) {
          channel.setMessageHandler { _, reply ->
            api.purchases() { result: Result<PurchasesResponse> ->
              val error = result.exceptionOrNull()
              if (error != null) {
                reply.reply(wrapError(error))
              } else {
                val data = result.getOrNull()
                reply.reply(wrapResult(data))
              }
            }
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.flutter_rustore_billing.RustoreBilling.purchase", codec)
        if (api != null) {
          channel.setMessageHandler { message, reply ->
            val args = message as List<Any?>
            val idArg = args[0] as String
            val developerPayloadArg = args[1] as String?
            api.purchase(idArg, developerPayloadArg) { result: Result<PaymentResult> ->
              val error = result.exceptionOrNull()
              if (error != null) {
                reply.reply(wrapError(error))
              } else {
                val data = result.getOrNull()
                reply.reply(wrapResult(data))
              }
            }
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.flutter_rustore_billing.RustoreBilling.purchaseInfo", codec)
        if (api != null) {
          channel.setMessageHandler { message, reply ->
            val args = message as List<Any?>
            val idArg = args[0] as String
            api.purchaseInfo(idArg) { result: Result<Purchase> ->
              val error = result.exceptionOrNull()
              if (error != null) {
                reply.reply(wrapError(error))
              } else {
                val data = result.getOrNull()
                reply.reply(wrapResult(data))
              }
            }
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.flutter_rustore_billing.RustoreBilling.confirm", codec)
        if (api != null) {
          channel.setMessageHandler { message, reply ->
            val args = message as List<Any?>
            val idArg = args[0] as String
            api.confirm(idArg) { result: Result<ConfirmPurchaseResponse> ->
              val error = result.exceptionOrNull()
              if (error != null) {
                reply.reply(wrapError(error))
              } else {
                val data = result.getOrNull()
                reply.reply(wrapResult(data))
              }
            }
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
    }
  }
}

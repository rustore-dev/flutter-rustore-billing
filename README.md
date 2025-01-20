# flutter_rustore_billing

Flutter RuStoreSDK для подключения платежей

## [Документация RuStore](https://help.rustore.ru/rustore/for_developers/developer-documentation/sdk_payments/sdk_payments_flutter)

### Содержание

- [flutter\_rustore\_billing](#flutter_rustore_billing)
  - [Документация RuStore](#документация-rustore)
    - [Содержание](#содержание)
    - [Подготовка требуемых параметров](#подготовка-требуемых-параметров)
    - [Пример реализации](#пример-реализации)
    - [Настройка примера приложения](#настройка-примера-приложения)
    - [Условия работы платежей](#условия-работы-платежей)
  - [Подключение в проект](#подключение-в-проект)
    - [Обработка deeplink](#обработка-deeplink)
  - [Инициализация](#инициализация)
  - [Проверка доступности работы с платежами](#проверка-доступности-работы-с-платежами)
  - [Работа с продуктами](#работа-с-продуктами)
    - [Получение списка продуктов](#получение-списка-продуктов)
  - [Работа с покупками](#работа-с-покупками)
    - [Получение списка покупок](#получение-списка-покупок)
    - [Покупка продукта](#покупка-продукта)
    - [Потребление (подтверждение) покупки](#потребление-подтверждение-покупки)
  - [Тестовые данные](#тестовые-данные)
  - [Условия распространения](#условия-распространения)
    - [Техническая поддержка](#техническая-поддержка)

### Подготовка требуемых параметров

Для корректной настройки примера приложения вам следует подготовить:

1. `consoleApplicationId` - код приложения из консоли разработчика RuStore (пример: <https://console.rustore.ru/apps/123456>), тут `consoleApplicationId` = 123456
2. `applicationId` - из приложения, которое вы публиковали в консоль RuStore, находится в файле build.gradle вашего проекта

   ```
    android {
       defaultConfig {
       applicationId = "ru.rustore.sdk.billingexample"
       }
    }
   ```

3. `availableProductIds` - [подписки](https://www.rustore.ru/help/developers/monetization/create-app-subscription/) и [разовые покупки](https://www.rustore.ru/help/developers/monetization/create-paid-product-in-application/) доступные в вашем приложении
4. `release.keystore` - подпись, которой было подписано приложение, опубликованное в консоль RuStore.
5. `release.properties` - в этом файле должны быть указаны параметры подписи, которой было подписано приложение, опубликованное в консоль RuStore. [Как работать с ключами подписи APK-файлов](https://www.rustore.ru/help/developers/publishing-and-verifying-apps/app-publication/apk-signature/)

### Пример реализации

Для того, чтобы узнать как правильно интегрировать платежи, рекомендуется ознакомиться с [приложением-примером](https://gitflic.ru/project/rustore/flutter-rustore-billing/file?file=example)

### Условия работы платежей

Для работы проведения платежей необходимо соблюдение следующих условий:

- RuStore должен поддерживать функциональность платежей.  
- Приложение не должно быть заблокированы в RuStore.
- Для приложения должна быть включена возможность покупок в консоли разработчика RuStore.

## Подключение в проект

Для подключения пакета к проекту нужно выполнить команду

```
flutter pub add flutter_rustore_billing
```

Эта команда добавит строчку в файл pubspec.yaml

```
dependencies:
    flutter_rustore_billing: ^7.0.1
```

### Обработка deeplink

Для корректной работы оплаты через сторонние приложения (СБП или SberPay), вам необходимо правильно реализовать обработку deeplink. Для этого необходимо указать в AndroidManifest.xml intent-filter с scheme вашего проекта:

```
<activity
android:name=".sample.MainActivity">

    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
  
    <intent-filter>
        <action android:name="android.intent.action.VIEW" />
        <category android:name="android.intent.category.DEFAULT" />
        <category android:name="android.intent.category.BROWSABLE" />
        <data android:scheme="yourappscheme" />
    </intent-filter>

</activity>
```

где "yourappscheme" - схема вашего deeplink, может быть изменена на другую.

Эта схема должна совпадать со схемой, передаваемым в методе `initialize()`.

## Инициализация

Перед вызовом методов библиотеки необходимо выполнить ее инициализацию. Для инициализации вызовете метод `RustoreBillingClient.initialize()`:

```
RustoreBillingClient.initialize(
    "123456",
    "yourappscheme://iamback",
    ).then((value) {
        print("initialize success: $value");
    }, onError: (err) {
        print("initialize err: $err");
});
```

123456 - код приложения из консоли разработчика RuStore (пример: <https://console.rustore.ru/apps/123456>).
yourappscheme://iamback - cхема deeplink, необходимая для возврата в ваше приложение после оплаты через стороннее приложение (например, SberPay или СБП). SDK генерирует свой хост к данной схеме.
Важно, чтобы схема deeplink, передаваемая в deeplinkScheme, совпадала со схемой, указанной в AndroidManifest.xml в разделе "Обработка deeplink".

## Проверка доступности работы с платежами

Для проверки доступности платежей необходимы следующие условия:

- На устройстве пользователя должен быть установлен RuStore.
- RuStore должен поддерживать функциональность платежей.  
- Пользователь должен быть авторизован в RuStore.
- Пользователь и приложение не должны быть заблокированы в RuStore.  
- Для приложения должна быть включена возможность покупок в консоли разработчика RuStore.
- Если все условия выполняются, метод `RustoreBillingClient.available()` возвращает значение true.

```
RustoreBillingClient.available().then((value) {
        print("available success $value");
    }, onError: (err) {
        print("available err: $err");
});
```

## Работа с продуктами

### Получение списка продуктов

Для получения продуктов необходимо использовать метод `RustoreBillingClient.products(ids)`:

```
RustoreBillingClient.products(ids).then((response) {
        for (final product in response.products) {
            print(product?.productId);
        }
    }, onError: (err) {
        print("products err: $err");
});
```

`ids: List<String?>` - список идентификаторов продуктов.

Метод возвращает `ProductsResponse`:

```
class ProductsResponse {
    int code;
    String? errorMessage;
    String? errorDescription;
    String? traceId;
    List<Product?> products;
    List<DigitalShopGeneralError?> errors;
}
```

- code - код ответа.
- errorMessage - сообщение об ошибке.
- errorDescription - описание ошибки.
- traceId - идентификатор ошибки.
- errors - список ошибок.
- products - список продуктов.

Структура ошибки `DigitalShopGeneralError`:

```
class DigitalShopGeneralError {
    String? name;
    int? code;
    String? description
}
```

- name - имя ошибки.
- code - код ошибки.
- description - описание ошибки.

Структура продукта `Product`:

```
class Product {
    String productId;
    String? productType;
    String productStatus;
    String? priceLabel;
    int? price;
    String? currency;
    String? language;
    String? title;
    String? description;
    String? imageUrl;
    String? promoImageUrl;
    Subscription? subscription;
}
```

- productId - идентификатор продукта.
- productType - тип продукта.
- productStatus - статус продукта.
- priceLabel - отформатированная цена товара, включая валютный знак на языке [language].
- price - цена в минимальных единицах.
- currency - код валюты ISO 4217.
- language - язык, указанный с помощью BCP 47 кодирования.
- title - название продукта на языке [language].
- description - описание продукта на языке [language].
- imageUrl - ссылка на картинку.
- promoImageUrl - ссылка на промо картинку.
- subscription - описание подписки, возвращается только для продуктов с типом subscription.

Структура подписки `Subscription`:

```
class Subscription {
    SubscriptionPeriod? subscriptionPeriod;
    SubscriptionPeriod? freeTrialPeriod;
    SubscriptionPeriod? gracePeriod;
    String? introductoryPrice;
    String? introductoryPriceAmount;
    SubscriptionPeriod? introductoryPricePeriod;
}
```

- subscriptionPeriod - период подписки.
- freeTrialPeriod - пробный период подписки.
- gracePeriod - льготный период подписки.
- introductoryPrice - отформатированная вступительная цена подписки, включая знак валюты, на языке product:language.
- introductoryPriceAmount - вступительная цена в минимальных единицах валюты (в копейках).
- introductoryPricePeriod - расчетный период вступительной цены.

Структура периода подписки `SubscriptionPeriod`:

```
class SubscriptionPeriod {
    int years;
    int months;
    int days;
}
```

- years - количество лет.
- months - количество месяцев.
- days - количество дней.

## Работа с покупками

### Получение списка покупок

Для получения списка покупок необходимо использовать метод `RustoreBillingClient.purchases()`:

```
RustoreBillingClient.purchases().then((response) {
        for (final product in response.purchases) {
            print(product?.purchaseId);
        }
    }, onError: (err) {
        print("purchases err: $err");
});
```

Метод возвращает `PurchasesResponse`:

```
class PurchasesResponse {
    int code;
    String? errorMessage;
    String? errorDescription;
    String? traceId;
    List<Purchase?> purchases;
    List<DigitalShopGeneralError?> errors;
}
```

- code - код ответа.
- errorMessage - сообщение об ошибке.
- errorDescription - описание ошибки.
- traceId - идентификатор ошибки.
- errors - список ошибок.
- purchases - список покупок.

Структура ошибки `DigitalShopGeneralError`:

```
class DigitalShopGeneralError {
    String? name;
    int? code;
    String? description;
}
```

- name - наименование ошибки.
- code - код ошибки.
- description - описание ошибки.

Структура покупки `Purchase`:

```
class Purchase {
    String? purchaseId;
    String? productId;
    String? productType;
    String? language;
    String? purchaseTime;
    String? orderId;
    String? amountLabel;
    int? amount;
    String? currency;
    int? quantity;
    String? purchaseState;
    String? developerPayload;
    String? invoiceId;
    String? subscriptionToken;
}
```

- purchaseId - идентификатор покупки.
- productId - идентификатор продукта.
- productType - тип продукта.
- description - описание покупки.
- language - язык, указанный с помощью BCP 47 кодирования.
- purchaseTime - время покупки (в формате RFC 3339).
- orderId - уникальный идентификатор оплаты, сформированный приложением (uuid).
- amountLabel - отформатированная цена покупки, включая валютный знак на языке [language].
- amount - цена в минимальных единицах валюты.
- currency - код валюты ISO 4217.
- quantity - количество продукта.
- purchaseState - состояние покупки.
  - CREATED - создана.
  - INVOICE_CREATED - создана, ожидает оплаты.
  - CONFIRMED - подтверждена.
  - PAID - оплачена.
  - CANCELLED - покупка отменена.
  - CONSUMED - потребление покупки подтверждено.
  - CLOSED - подписка была отменена.
- developerPayload - указанная разработчиком строка, содержащая дополнительную информацию о заказе.
- invoiceId - идентификатор счёта.
- subscriptionToken - токен для валидации покупки на сервере.

### Покупка продукта

Для вызова покупки продукта используйте метод `RustoreBillingClient.purchase(id)`:

```
RustoreBillingClient.purchase(id).then((response) {
        print("purchase success: $response");
    }, onError: (err) {
        print("purchase err: $err");
});
```

- id - идентификатор продукта.

Структура результата покупки PaymentResult:

```
class PaymentResult {
    SuccessInvoice? successInvoice;
    InvalidInvoice? invalidInvoice;
    SuccessPurchase? successPurchase;
    InvalidPurchase? invalidPurchase;
}
```

Структура `SuccessInvoice`:

```
class SuccessInvoice {
    String invoiceId;
    String finishCode;
}
```

Структура `InvalidInvoice`:

```
class InvalidInvoice {
    String? invoiceId;
}
```

Структура `SuccessPurchase`:

```
class SuccessPurchase {
    String finishCode;
    String? orderId;
    String purchaseId;
    String productId;
}
```

Структура `InvalidPurchase`:

```
class InvalidPurchase {
    String? purchaseId;
    String? invoiceId;
    String? orderId;
    int? quantity;
    String? productId;
    int? errorCode;
}
```

- `SuccessInvoice` - платежи завершились с результатом.
- `InvalidInvoice` - платежи завершились без указания инвойса. Вероятно, они были запущены с некорректным инвойсом (пустая строка, например).
- `SuccessPurchase` - результат успешного завершения покупки цифрового товара.
- `InvalidPurchase` - при оплате цифрового товара платежи завершились c ошибкой.

Возможные статусы, которые может содержать `finishCode`:

- SUCCESSFUL_PAYMENT - успешная оплата.
- CLOSED_BY_USER - отменено пользователем.
- UNHANDLED_FORM_ERROR - неизвестная ошибка.
- PAYMENT_TIMEOUT - ошибка оплаты по таймауту.
- DECLINED_BY_SERVER - отклонено сервером.
- RESULT_UNKNOWN - неизвестный статус оплаты.

### Потребление (подтверждение) покупки

RuStore содержит продукты следующих типов:

- CONSUMABLE - потребляемый (можно купить много раз, например кристаллы в приложении).
- NON_CONSUMABLE - непотребляемый (можно купить один раз, например отключение рекламы в приложении).
- SUBSCRIPTION - подписка (можно купить на период времени, например подписка в стриминговом сервисе).

Потребления требуют только продукты типа CONSUMABLE, если они находятся в состоянии PurchaseState.PAID.

Для потребления покупки вы можете использовать метод `RustoreBillingClient.confirm(id)`:

```
RustoreBillingClient.confirm(id).then((response) {
        print("confirm success: $response");
    }, onError: (err) {
        print("confirm err: $err");
});
```

- id - идентификатор покупки.

Метод возвращает `ConfirmPurchaseResponse`:

```
class ConfirmPurchaseResponse {
    int code;
    String? errorMessage;
    String? errorDescription;
    String? traceId;
    List<DigitalShopGeneralError?> errors;
}
```

- code - код ответа.
- errorMessage - сообщение об ошибке для пользователя.
- errorDescription - расшифровка сообщения об ошибке.
- traceId - идентификатор ошибочного сообщения.
- errors - список ошибок.

Структура ошибки `DigitalShopGeneralError`:

```
class DigitalShopGeneralError {
    String? name;
    int? code;
    String? description;
}
```

- name - наименование атрибута ошибки.
- code - код ошибки.
- description - описание ошибки.

## Тестовые данные

[Ссылка](https://securepayments.sberbank.ru/wiki/doku.php/test_cards) на тестовые банковские карты.

## Условия распространения

Данное программное обеспечение, включая исходные коды, бинарные библиотеки и другие файлы распространяется под лицензией MIT. Информация о лицензировании доступна в документе `MIT-LICENSE.txt`

### Техническая поддержка

Если появились вопросы по интеграции SDK платежей, обратитесь по [ссылке](https://www.rustore.ru/help/sdk/payments).

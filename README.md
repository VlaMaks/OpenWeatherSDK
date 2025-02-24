# OpenWeatherSDK

&#x20;

OpenWeatherSDK - это Java SDK для удобного взаимодействия с OpenWeather API (https://openweathermap.org/current).

## 🚀 Возможности

- Получение текущей погоды по названию города
- Поддержка обработки ошибок

## 📖 Документация

### `WeatherSDKFactory`

Этот класс предоставляет фабричные методы для управления экземплярами `OpenWeatherSDK`.

#### Методы:

- `getInstance(String apiKey, boolean pollingMode)`: Возвращает экземпляр `OpenWeatherSDK` для указанного API-ключа. Если экземпляр уже существует, он будет переиспользован.

    - `apiKey` - API-ключ OpenWeather.
    - `pollingMode` - определяет режим работы API. true - опрос каждые 10 минут для обновления информации о погоде, false - режим запроса.
    - может выбросить ApiKeyException, если передан пустой или null apiKey

- `removeInstance(OpenWeatherSDK weatherSDK)`: Удаляет экземпляр `OpenWeatherSDK`.

    - `weatherSDK` - Экземпляр, который нужно удалить.

### `OpenWeatherSDK`

Этот класс представляет SDK для взаимодействия с OpenWeather API.
#### Методы:

- `getWeather(String city)`: Возвращает текущую погоду для указанного города.
    - `city` - Название города.
    - Может выбросить `WeatherApiException`, если возникла ошибка при запросе.


## 🔧 Использование

### Создание экземпляра OpenWeatherSDK

```java
OpenWeatherSDK openWeatherSDK = WeatherSDKFactory.getInstance("YOUR_API_KEY", false);
```

### Получение текущей погоды

```java
String weatherJson = openWeatherSDK.getWeather("Челябинск");
```

### Пример результата

```json
{
  "weather": {
    "description": "light snow",
    "main": "Snow"
  },
  "sys": {
    "sunrise": 1740365827,
    "sunset": 1740403108
  },
  "main": {
    "temp": 263.05,
    "feelsLike": 256.05
  },
  "wind": {
    "speed": 6
  },
  "visibility": 3400,
  "dt": 1740408063,
  "timezone": 18000,
  "name": "Chelyabinsk"
}

```





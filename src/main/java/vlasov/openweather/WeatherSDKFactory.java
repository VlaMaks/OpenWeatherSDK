package vlasov.openweather;

import vlasov.openweather.data.CachedWeather;
import vlasov.openweather.exceptions.ApiKeyException;
import vlasov.openweather.validations.ApiKeyValidator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
* Фабрика для управления экземплярами {@link OpenWeatherSDK}.
* <p>
 * Позволяет создавать, получать и удалять экземпляры SDK с учетом переданного API-ключа.
 */
public class WeatherSDKFactory {
    /**
     * Хранит экземпляры OpenWeatherSDK, привязанные к API-ключам.
     */
    private static final Map<String, OpenWeatherSDK> instances = new ConcurrentHashMap<>();
    /**
     * Кэш для хранения данных о погоде.
     */
    private static final ConcurrentHashMap<String, CachedWeather> weatherCache = new ConcurrentHashMap<>(10);

    /**
     * Возвращает экземпляр {@link OpenWeatherSDK} для указанного API-ключа.
     * Если экземпляр уже существует, он будет переиспользован.
     *
     * @param apiKey      API-ключ OpenWeather.
     * @param pollingMode режим работы API. true - режим опроса, fase - режим запроса
     * @return Экземпляр {@link OpenWeatherSDK}.
     * @throws ApiKeyException Если API-ключ недействителен.
     */
    public static OpenWeatherSDK getInstance(String apiKey, boolean pollingMode) throws ApiKeyException {
        ApiKeyValidator.validate(apiKey);
        return instances.computeIfAbsent(apiKey, key -> new OpenWeatherSDK(key, pollingMode, weatherCache));
    }

    /**
     * Удаляет экземпляр {@link OpenWeatherSDK}.
     * Если экземпляр работал в режиме опроса, перед удалением вызывается метод {@code shutDown()}.
     *
     * @param weatherSDK Экземпляр, который нужно удалить.
     */
    public static void removeInstance(OpenWeatherSDK weatherSDK) {
        if (weatherSDK != null) {
            if (weatherSDK.isPollingMode()) {
                weatherSDK.shutDown();
            }
            instances.remove(weatherSDK.getApiKey());
        }
    }

    private WeatherSDKFactory() {
    }
}


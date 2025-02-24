package vlasov.openweather;

import vlasov.openweather.data.CachedWeather;
import vlasov.openweather.validations.ApiKeyValidator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WeatherSDKFactory {
    private static final Map<String, OpenWeatherSDK> instances = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, CachedWeather> weatherCache = new ConcurrentHashMap<>(10);

    public static OpenWeatherSDK getInstance(String apiKey, boolean pollingMode) {
        ApiKeyValidator.validate(apiKey);
        return instances.computeIfAbsent(apiKey, key -> new OpenWeatherSDK(key, pollingMode, weatherCache));
    }

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


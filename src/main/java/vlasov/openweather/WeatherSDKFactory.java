package vlasov.openweather;

import vlasov.openweather.validations.ApiKeyValidator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WeatherSDKFactory {
    private static final Map<String, OpenWeatherSDK> instances = new ConcurrentHashMap<>();

    public static OpenWeatherSDK getInstance(String apiKey, boolean pollingMode) {
        ApiKeyValidator.validate(apiKey);
        return instances.computeIfAbsent(apiKey, key -> new OpenWeatherSDK(key, pollingMode));
    }

    public static void removeInstance(String apiKey) {
        OpenWeatherSDK sdk = instances.get(apiKey);
        if (sdk != null) {
            if (sdk.isPollingMode())
            {
                sdk.shutDown();
            }
        }
        instances.remove(apiKey);
    }
}


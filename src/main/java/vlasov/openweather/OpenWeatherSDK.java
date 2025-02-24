package vlasov.openweather;

import lombok.Getter;
import vlasov.openweather.data.CachedWeather;
import vlasov.openweather.exceptions.WeatherApiException;

import vlasov.openweather.service.OpenWeatherService;
import vlasov.openweather.validations.CityValidator;

import java.util.concurrent.ConcurrentHashMap;


public class OpenWeatherSDK {
    private final OpenWeatherService openWeatherService;
    @Getter
    private final String apiKey;
    private final boolean pollingMode;

    OpenWeatherSDK(String apiKey, boolean pollingMode, ConcurrentHashMap<String, CachedWeather> weatherCache) {
        this.openWeatherService = new OpenWeatherService(apiKey, pollingMode, weatherCache);
        this.apiKey = apiKey;
        this.pollingMode = pollingMode;
    }

    protected boolean isPollingMode() {
        return pollingMode;
    }

    protected void shutDown() {
        openWeatherService.shutdown();
    }

    public String getWeather(String city) throws WeatherApiException {
        CityValidator.validate(city);
        return openWeatherService.getWeather(apiKey,city);
    }

}

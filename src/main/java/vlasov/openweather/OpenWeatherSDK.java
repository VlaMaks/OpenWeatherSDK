package vlasov.openweather;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import vlasov.openweather.service.OpenWeatherService;
import vlasov.openweather.validations.CityValidator;

public class OpenWeatherSDK {
    private final OpenWeatherService openWeatherService;

    OpenWeatherSDK(String apiKey, boolean pollingMode) {
        this.openWeatherService = new OpenWeatherService(apiKey, pollingMode);
    }

    protected boolean isPollingMode() {
        return openWeatherService.isPollingMode();
    }

    protected void shutDown() {
        openWeatherService.shutdown();
    }

    public String getWeather(String city)  {
        CityValidator.validate(city);
        return new ObjectMapper().writeValueAsString(openWeatherService.getWeather(city));
    }
}

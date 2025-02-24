package vlasov.openweather.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import vlasov.openweather.data.WeatherData;


public class WeatherConverter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String convertToString(WeatherData weatherData) throws JsonProcessingException {
        return objectMapper.writeValueAsString(weatherData);
    }

    public static WeatherData convertToWeatherData(String weatherDataStr) throws JsonProcessingException {
        return objectMapper.readValue(weatherDataStr, WeatherData.class);
    }
}

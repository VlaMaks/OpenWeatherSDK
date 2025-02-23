package vlasov.openweather.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.fasterxml.jackson.databind.ObjectMapper;
import vlasov.openweather.data.WeatherData;

public class OpenWeatherClient {

    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    public WeatherData fetchWeather(String city, String apiKey) throws Exception {
        String urlString = String.format("%s?q=%s&appid=%s", BASE_URL, city, apiKey);
        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper.readValue(content.toString(), WeatherData.class);

        } catch (Exception e) {
            throw new Exception("Error fetching weather data: " + e.getMessage());
        }
    }
}
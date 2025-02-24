package vlasov.openweather.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import vlasov.openweather.converters.CityDataConverter;
import vlasov.openweather.converters.WeatherConverter;
import vlasov.openweather.data.CityData;
import vlasov.openweather.data.WeatherData;
import vlasov.openweather.exceptions.WeatherApiException;

class OpenWeatherClient {

    private static final String BASE_URL_WEATHER = "https://api.openweathermap.org/data/2.5/weather";
    private static final String BASE_URL_GEOCODING = "http://api.openweathermap.org/geo/1.0/direct";

    public String getWeather(String apiKey, String city) throws WeatherApiException {
        CityData cityData = fetchCoordinates(apiKey, city);
        return fetchWeather(apiKey, cityData);
    }

    private String fetchWeather(String apiKey, CityData cityData) throws WeatherApiException {
        String urlString = String.format("%s?lat=%s&lon=%s&appid=%s", BASE_URL_WEATHER, cityData.getLatitude(), cityData.getLongitude(), apiKey);
        HttpURLConnection connection = getConnection(urlString);

        String content = readData(connection);
        WeatherData data;

        try {
            data = WeatherConverter.convertToWeatherData(content);
        } catch (JsonProcessingException e) {
            throw new WeatherApiException("Ошибка во время преобразования полученных данных о погоде в объект WeatherData", e);
        }

        String result;
        try {
            result = WeatherConverter.convertToString(data);
        } catch (JsonProcessingException e) {
            throw new WeatherApiException("Ошибка во время преобразования объекта WeatherData в строку", e);
        }

        return result;
    }

    private CityData fetchCoordinates(String apiKey, String city) throws WeatherApiException {

        String urlString = String.format("%s?q=%s&limit=%s&appid=%s", BASE_URL_GEOCODING, city, 1, apiKey);
        HttpURLConnection connection = getConnection(urlString);
        String content = readData(connection);

        CityData[] citiesData;
        CityData cityData;
        try {
            citiesData = CityDataConverter.convertToCityData(content);
            cityData = citiesData[0];
        } catch (JsonProcessingException e) {
            throw new WeatherApiException("Ошибка во время преобразования полученных данных о погоде в объект WeatherData", e);
        }

        return cityData;
    }

    private String readData(HttpURLConnection connection) throws WeatherApiException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

        } catch (Exception e) {
            throw new WeatherApiException("Ошибка при получении информации о погоде", e);
        }

        return content.toString();
    }

    private HttpURLConnection getConnection(String urlStr) throws WeatherApiException {
        URL url = null;
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
        }

        HttpURLConnection connection;
        try {
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            throw new WeatherApiException("Не удалось установить соединение", e);
        }

        try {
            connection.setRequestMethod("GET");
        } catch (ProtocolException e) {
        }

        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        return connection;
    }
}
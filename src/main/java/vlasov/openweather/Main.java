package vlasov.openweather;

import com.fasterxml.jackson.core.JsonProcessingException;
import vlasov.openweather.exceptions.WeatherApiException;


public class Main {
    public static void main(String[] args) throws WeatherApiException{

        OpenWeatherSDK openWeatherSDK = WeatherSDKFactory.getInstance("0e4aab179a6c7264749e8c16fc5210c4",  false);
        String res = openWeatherSDK.getWeather("Челябинск");
        System.out.println(res);

    }
}
package vlasov.openweather.service;

import lombok.Getter;
import vlasov.openweather.data.WeatherData;

import java.time.Instant;

class CachedWeather {
    @Getter
    private final WeatherData weatherData;
    private final Instant timestamp;

    CachedWeather(WeatherData weatherData) {
        this.weatherData = weatherData;
        this.timestamp = Instant.now();
    }

    boolean isValid() {
        return Instant.now().minusSeconds(600).isBefore(timestamp);
    }
}

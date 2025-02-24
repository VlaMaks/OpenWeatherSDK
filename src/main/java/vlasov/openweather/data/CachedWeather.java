package vlasov.openweather.data;

import lombok.Getter;

import java.time.Instant;
import java.util.Objects;

public class CachedWeather {
    @Getter
    private final String weatherData;
    private final Instant timestamp;

    public CachedWeather(String weatherData) {
        this.weatherData = weatherData;
        this.timestamp = Instant.now();
    }

    public boolean isValid() {
        return Instant.now().minusSeconds(600).isBefore(timestamp);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CachedWeather that = (CachedWeather) o;
        return Objects.equals(weatherData, that.weatherData) && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(weatherData, timestamp);
    }
}

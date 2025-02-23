package vlasov.openweather.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import vlasov.openweather.data.WeatherData;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class OpenWeatherService {
    private final String apiKey;

    @Getter
    private final boolean pollingMode;

    private final OpenWeatherClient apiClient;
    private final ConcurrentHashMap<String, CachedWeather> weatherCache;
    private final ScheduledExecutorService scheduler;
    private final int pollingIntervalMinutes;

    public OpenWeatherService(String apiKey, boolean pollingMode) {
        this.apiKey = apiKey;
        this.pollingMode = pollingMode;
        weatherCache = new ConcurrentHashMap<>(10);
        scheduler = Executors.newScheduledThreadPool(1);
        pollingIntervalMinutes = 10;
        apiClient = new OpenWeatherClient();

        if (this.pollingMode) {
            startPolling();
        }
    }

    public WeatherData getWeather(String city) {
        CachedWeather cached = weatherCache.get(city);
        if (cached != null && cached.isValid()) {
            return cached.getWeatherData();
        }
        WeatherData newData = null;

        try {
            newData = apiClient.fetchWeather(city, apiKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        weatherCache.put(city, new CachedWeather(newData));
        return newData;
    }

    private void startPolling() {
        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("Updating weather data in polling mode...");
            for (String city : weatherCache.keySet()) {
                try {
                    WeatherData updatedData = apiClient.fetchWeather(city, apiKey);
                    weatherCache.put(city, new CachedWeather(updatedData));
                } catch (Exception e) {
                    System.err.println("Error updating weather for " + city + ": " + e.getMessage());
                }
            }
        }, 0, pollingIntervalMinutes, TimeUnit.MINUTES);
    }

    public void shutdown() {
        scheduler.shutdown();
    }

}

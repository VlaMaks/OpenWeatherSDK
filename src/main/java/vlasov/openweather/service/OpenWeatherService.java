package vlasov.openweather.service;

import vlasov.openweather.data.CachedWeather;
import vlasov.openweather.exceptions.WeatherApiException;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class OpenWeatherService {

    private final OpenWeatherClient apiClient;
    private final ConcurrentHashMap<String, CachedWeather> weatherCache;
    private final ScheduledExecutorService scheduler;
    private final int pollingIntervalMinutes;

    public OpenWeatherService(String apiKey, boolean pollingMode, ConcurrentHashMap<String, CachedWeather> weatherCache) {
        this.weatherCache = weatherCache;
        scheduler = Executors.newScheduledThreadPool(1);
        pollingIntervalMinutes = 10;
        apiClient = new OpenWeatherClient();

        if (pollingMode) {
            startPolling(apiKey);
        }
    }

    public String getWeather(String apiKey, String city) throws WeatherApiException {
        city = city.strip().toLowerCase();
        CachedWeather cached = weatherCache.get(city);
        if (cached != null && cached.isValid()) {
            return cached.getWeatherData();
        }
        String newData = apiClient.getWeather(apiKey, city);

        if (weatherCache.size() > 9) {
            String keyForDelete = "";
            for (String key : weatherCache.keySet()) {
                keyForDelete = key;
                break;
            }

            weatherCache.remove(keyForDelete);
        }

        weatherCache.put(city, new CachedWeather(newData));

        return newData;
    }

    private void startPolling(String apiKey) {
        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("Updating weather data in polling mode...");
            for (String city : weatherCache.keySet()) {
                try {
                    String updatedData = apiClient.getWeather(apiKey, city);
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OpenWeatherService that = (OpenWeatherService) o;
        return pollingIntervalMinutes == that.pollingIntervalMinutes && Objects.equals(apiClient, that.apiClient) && Objects.equals(weatherCache, that.weatherCache) && Objects.equals(scheduler, that.scheduler);
    }

    @Override
    public int hashCode() {
        return Objects.hash(apiClient, weatherCache, scheduler, pollingIntervalMinutes);
    }
}

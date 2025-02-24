package vlasov.openweather.service;

import vlasov.openweather.data.CachedWeather;
import vlasov.openweather.exceptions.WeatherApiException;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Сервис для получения и кэширования данных о погоде с API OpenWeather.
 * Поддерживает режим опроса для периодического обновления данных о погоде для городов.
 */
public class OpenWeatherService {

    private final OpenWeatherClient apiClient;
    private final ConcurrentHashMap<String, CachedWeather> weatherCache;
    private final ScheduledExecutorService scheduler;
    private final int pollingIntervalMinutes;

    /**
     * Конструктор для создания экземпляра OpenWeatherService с заданными параметрами.
     *
     * @param apiKey ключ API для доступа к OpenWeather.
     * @param pollingMode если true, включается режим опроса, и данные о погоде обновляются периодически.
     * @param weatherCache кэш для хранения данных о погоде для городов.
     */
    public OpenWeatherService(String apiKey, boolean pollingMode, ConcurrentHashMap<String, CachedWeather> weatherCache) {
        this.weatherCache = weatherCache;
        scheduler = Executors.newScheduledThreadPool(1);
        pollingIntervalMinutes = 10;
        apiClient = new OpenWeatherClient();

        if (pollingMode) {
            startPolling(apiKey);
        }
    }

    /**
     * Получает данные о погоде для указанного города.
     * Сначала проверяет кэш, и если данные актуальны, возвращает кэшированную информацию.
     * Если данных нет или они устарели, запрашивает новые данные через API.
     *
     * @param apiKey ключ API для доступа к OpenWeather.
     * @param city город, для которого нужно получить данные о погоде.
     * @return данные о погоде для указанного города типа String
     * @throws WeatherApiException если возникает ошибка при запросе данных о погоде с API.
     */

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

    /**
     * Запускает процесс опроса, который периодически обновляет данные о погоде для всех городов в кэше.
     * Это происходит с интервалом, заданным в {@code pollingIntervalMinutes}.
     *
     * @param apiKey ключ API для доступа к OpenWeather.
     */
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

    /**
     * Завершает работу с процессом опроса и освобождает все связанные ресурсы.
     */
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

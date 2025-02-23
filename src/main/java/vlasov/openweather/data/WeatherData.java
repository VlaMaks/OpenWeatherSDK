package vlasov.openweather.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WeatherData {
    private final Weather weather;
    private final Sys sys;
    private final Temperature temperature;
    private final Wind wind;
    private final int visibility;
    private final long dateTime;
    private final long timezone;
    private final String name;
}

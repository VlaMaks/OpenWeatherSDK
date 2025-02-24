package vlasov.openweather.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Objects;


@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherData {

    @JsonProperty(value = "weather", access = JsonProperty.Access.WRITE_ONLY)
    private List<Weather> weather;

    @JsonProperty(value = "weather", access = JsonProperty.Access.READ_ONLY)
    private Weather weatherElement;

    @JsonProperty("sys")
    private Sys sys;

    @JsonProperty("main") // В JSON температура хранится в объекте "main"
    private Temperature temperature;

    @JsonProperty("wind")
    private Wind wind;

    @JsonProperty("visibility")
    private int visibility;

    @JsonProperty("dt")
    private long dateTime;

    @JsonProperty("timezone")
    private long timezone;

    @JsonProperty("name")
    private String name;

    @JsonCreator
    public WeatherData(@JsonProperty("weather") List<Weather> weather,
                       @JsonProperty("sys") Sys sys,
                       @JsonProperty("main") Temperature temperature, // JSON использует "main"
                       @JsonProperty("wind") Wind wind,
                       @JsonProperty("visibility") int visibility,
                       @JsonProperty("dt") long dateTime,
                       @JsonProperty("timezone") long timezone,
                       @JsonProperty("name") String name) {
        this.weather = weather;
        this.weatherElement = (weather != null && !weather.isEmpty()) ? weather.getFirst() : null;
        this.sys = sys;
        this.temperature = temperature;
        this.wind = wind;
        this.visibility = visibility;
        this.dateTime = dateTime;
        this.timezone = timezone;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        WeatherData that = (WeatherData) o;
        return visibility == that.visibility && dateTime == that.dateTime && timezone == that.timezone && Objects.equals(weather, that.weather) && Objects.equals(sys, that.sys) && Objects.equals(temperature, that.temperature) && Objects.equals(wind, that.wind) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(weather, sys, temperature, wind, visibility, dateTime, timezone, name);
    }
}

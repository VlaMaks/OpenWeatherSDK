package vlasov.openweather.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@Getter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
class Sys {
    private final long sunrise;
    private final long sunset;

    @JsonCreator
    Sys(@JsonProperty("sunrise") long sunrise, @JsonProperty("sunset") long sunset) {
        this.sunrise = sunrise;
        this.sunset = sunset;
    }
}

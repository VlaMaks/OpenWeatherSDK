package vlasov.openweather.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@Getter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
class Temperature {

    private final float temp;
    private final float feelsLike;

    @JsonCreator
    Temperature(@JsonProperty("temp") float temp, @JsonProperty("feels_like") float feelsLike) {
        this.temp = temp;
        this.feelsLike = feelsLike;
    }
}


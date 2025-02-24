package vlasov.openweather.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@Getter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
class Wind {
    private final float speed;

    @JsonCreator
    Wind(@JsonProperty("speed") float speed) {
        this.speed = speed;
    }
}

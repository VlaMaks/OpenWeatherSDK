package vlasov.openweather.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
class Weather {
    private final String main;
    private final String description;

    @JsonCreator
    Weather(@JsonProperty("description") String description, @JsonProperty("main") String main) {
        this.description = description;
        this.main = main;
    }
}

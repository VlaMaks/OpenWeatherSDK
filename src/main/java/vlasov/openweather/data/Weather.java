package vlasov.openweather.data;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@Setter
class Weather {
    private final String main;
    private final String description;
}

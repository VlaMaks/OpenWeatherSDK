package vlasov.openweather.data;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
class Temperature {
    private final float temp;
    private final float feelsLike;
}


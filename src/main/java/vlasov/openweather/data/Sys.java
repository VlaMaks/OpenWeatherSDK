package vlasov.openweather.data;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
class Sys {
    private final long sunrise;
    private final long sunset;
}

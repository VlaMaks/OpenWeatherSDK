package vlasov.openweather.validations;

import vlasov.openweather.exceptions.CityNotFoundException;

import java.util.Objects;

public class CityValidator {

    public static void validate(String city) {
        if (Objects.isNull(city) || city.isBlank()) {
            throw new CityNotFoundException("City must not be empty");
        }
    }
}

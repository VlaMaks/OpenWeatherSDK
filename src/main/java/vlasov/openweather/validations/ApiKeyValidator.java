package vlasov.openweather.validations;

import vlasov.openweather.exceptions.ApiKeyException;

import java.util.Objects;

public class ApiKeyValidator {
    public static void validate(String apiKey) {
        if (Objects.isNull(apiKey) || apiKey.isBlank()) {
            throw new ApiKeyException("API key must not be empty");
        }
    }
}

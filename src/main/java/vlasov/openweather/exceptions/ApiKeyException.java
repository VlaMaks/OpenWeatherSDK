package vlasov.openweather.exceptions;

public class ApiKeyException extends RuntimeException {
    public ApiKeyException(String message) {
        super(message);
    }
}

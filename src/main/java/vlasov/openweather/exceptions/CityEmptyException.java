package vlasov.openweather.exceptions;

public class CityEmptyException extends RuntimeException {
    public CityEmptyException(String message) {
        super(message);
    }
}

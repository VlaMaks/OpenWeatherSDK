package vlasov.openweather.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import vlasov.openweather.data.CityData;

public class CityDataConverter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String convertToString(CityData cityData) throws JsonProcessingException {
        return objectMapper.writeValueAsString(cityData);
    }

    public static CityData[] convertToCityData(String cityDataStr) throws JsonProcessingException {
        return objectMapper.readValue(cityDataStr, CityData[].class);
    }
}

package alexandregazur.tqs_hw_1;

import alexandregazur.tqs_hw_1.model.AirQuality;
import alexandregazur.tqs_hw_1.model.Request;
import alexandregazur.tqs_hw_1.service.AirQualityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServiceTests {
	private AirQualityService airQualityService;
	
	@BeforeEach
	void setUp() {
		airQualityService = mock(AirQualityService.class);
	}
	
	@Test
	void serviceTest() {
		String city = "Lisbon";
		
		Request requestData = new Request("Lisbon", "Lisboa", "Portugal", "2023-04-09 23:05",
				new AirQuality(217.0f, 3.9000000953674316f, 118.69999694824219f, 1.600000023841858f,
						17.799999237060547f, 25.200000762939453f, 2, 2));
		
		Map<String, Object> response = new LinkedHashMap<>();
		
		Map<String, Object> location = new LinkedHashMap<>();
		location.put("name", "Lisbon");
		location.put("region", "Lisboa");
		location.put("country", "Portugal");
		location.put("localtime", "2023-04-09 23:05");
		
		Map<String, Object> air_quality = new LinkedHashMap<>();
		air_quality.put("co", 217.0f);
		air_quality.put("no2", 3.9000000953674316f);
		air_quality.put("o3", 118.69999694824219f);
		air_quality.put("so2", 1.600000023841858f);
		air_quality.put("pm2_5", 17.799999237060547f);
		air_quality.put("pm10", 25.200000762939453f);
		air_quality.put("us-epa-index", 2);
		air_quality.put("gb-defra-index", 2);
		
		Map<String, Object> current = new LinkedHashMap<>();
		current.put("air_quality", air_quality);
		
		response.put("location", location);
		response.put("current", current);
		
		when(airQualityService.getResponse(anyString())).thenReturn(response);
		when(airQualityService.getCurrentAirQuality(anyString())).thenCallRealMethod();
		when(airQualityService.getForecastAirQuality(anyString(), anyInt())).thenCallRealMethod();
		Request result = airQualityService.getCurrentAirQuality(city);
		
		assertThat(result).isEqualTo(requestData);
	}
}

package alexandregazur.tqs_hw_1.controller;

import alexandregazur.tqs_hw_1.service.AirQualityService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AirQualityRestController {
	
	@Autowired
	private AirQualityService airQualityService;
	
	@GetMapping("/air-quality/{city}/{days}")
	public String showAirQuality(@PathVariable String city, @PathVariable int days) {
		return new Gson().toJson(airQualityService.getForecastAirQuality(city, days));
	}
	
	@GetMapping("/cache")
	public String showCache() {
		return AirQualityService.getCache().toString();
	}
}

package alexandregazur.tqs_hw_1.service;

import alexandregazur.tqs_hw_1.model.AirQuality;
import alexandregazur.tqs_hw_1.model.Cache;
import alexandregazur.tqs_hw_1.model.Request;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class AirQualityService {
	
	private static Cache cache = new Cache();
	
	@Value("${weather.api.key}")
	private String apiKey;
	private static final String API_URL_CURRENT = "https://api.weatherapi.com/v1/current.json?key=%s&q=%s&aqi=yes";
	private static final String API_URL_FORECAST = "https://api.weatherapi.com/v1/forecast.json?key=%s&q=%s&days=%d&aqi=yes&alerts=no";
	
	public Request getCurrentAirQuality(String city) {
		return getForecastAirQuality(city, 0);
	}
	
	public Request getForecastAirQuality(String city, int days) {
		Map<String, Object> response, location, air_quality;
		if(days == 0) {
			response = getResponse(city);
			air_quality = (LinkedHashMap<String, Object>) ((LinkedHashMap<String, Object>) response.get("current")).get("air_quality");
		} else {
			response = getResponse(city, days);
			
			ArrayList<Object> we;
			air_quality = (LinkedHashMap<String, Object>) response.get("forecast");
			we = (ArrayList<Object>) air_quality.get("forecastday");
			air_quality = (LinkedHashMap<String, Object>) ((LinkedHashMap<String, Object>) ((LinkedHashMap<String, Object>) we.get(we.size()-1)).get("day")).get("air_quality");
			
		}
		
		location = (LinkedHashMap<String, Object>) response.get("location");
		
		return new Request(city, (String) location.get("region"), (String) location.get("country"), (String) location.get("localtime"), new AirQuality(air_quality));
	}
	
	public Map<String, Object> getResponse(String city) {
		Map<String, Object> response = cache.sampleCache(city);
		if(response != null)
			return response;
		response = JsonPath.from(RestAssured.get(String.format(API_URL_CURRENT, apiKey, city)).asString()).getMap("$");
		if(response != null)
			cache.putInCache(city, response);
		return response;
	}
	
	public Map<String, Object> getResponse(String city, int days) {
		Map<String, Object> response = cache.sampleCache(city, days);
		if(response != null)
			return response;
		response = JsonPath.from(RestAssured.get(String.format(API_URL_FORECAST, apiKey, city, days)).asString()).getMap("$");
		if(response != null)
			cache.putInCache(city, days, response);
		return response;
	}
	
	public static Cache getCache() {
		return cache;
	}
	
}

package alexandregazur.tqs_hw_1.model;

import java.util.Objects;

public class Request {
	
	private String city;
	private String region;
	private String country;
	private String localTime;
	private AirQuality airQuality;
	
	public Request() {}
	
	public Request(String city, String region, String country, String localTime, AirQuality airQuality) {
		this.city = city;
		this.region = region;
		this.country = country;
		this.localTime = localTime;
		this.airQuality = airQuality;
	}
	
	@Override
	public String toString() {
		return "Request{" +
				"city='" + city + '\'' +
				", region='" + region + '\'' +
				", country='" + country + '\'' +
				", localTime='" + localTime + '\'' +
				", airQuality=" + airQuality +
				'}';
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		
		Request request = (Request) o;
		
		if (!Objects.equals(city, request.city)) return false;
		if (!Objects.equals(region, request.region)) return false;
		if (!Objects.equals(country, request.country)) return false;
		if (!Objects.equals(localTime, request.localTime)) return false;
		return Objects.equals(airQuality, request.airQuality);
	}
	
	@Override
	public int hashCode() {
		int result = city != null ? city.hashCode() : 0;
		result = 31 * result + (region != null ? region.hashCode() : 0);
		result = 31 * result + (country != null ? country.hashCode() : 0);
		result = 31 * result + (localTime != null ? localTime.hashCode() : 0);
		result = 31 * result + (airQuality != null ? airQuality.hashCode() : 0);
		return result;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getRegion() {
		return region;
	}
	
	public void setRegion(String region) {
		this.region = region;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getLocalTime() {
		return localTime;
	}
	
	public void setLocalTime(String localTime) {
		this.localTime = localTime;
	}
	
	public AirQuality getAirQuality() {
		return airQuality;
	}
	
	public void setAirQuality(AirQuality airQuality) {
		this.airQuality = airQuality;
	}
	
}


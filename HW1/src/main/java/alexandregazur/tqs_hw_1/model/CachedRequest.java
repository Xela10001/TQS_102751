package alexandregazur.tqs_hw_1.model;

import java.util.Objects;

public class CachedRequest {
	private String city;
	private int days;
	
	public CachedRequest(String city, int days) {
		this.city = city;
		this.days = days;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		
		CachedRequest that = (CachedRequest) o;
		
		if (days != that.days) return false;
		return Objects.equals(city, that.city);
	}
	
	@Override
	public int hashCode() {
		int result = city != null ? city.hashCode() : 0;
		result = 31 * result + days;
		return result;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public int getDays() {
		return days;
	}
	
	public void setDays(int days) {
		this.days = days;
	}
}

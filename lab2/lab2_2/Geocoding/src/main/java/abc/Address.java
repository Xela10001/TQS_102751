package abc;

public class Address {
	private String road;
	private String state;
	private String city;
	private String zio;
	private String houseNumber;
	
	public Address(String road, String state, String city, String zio, String houseNumber) {
		this.road = road;
		this.state = state;
		this.city = city;
		this.zio = zio;
		this.houseNumber = houseNumber;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		
		Address address = (Address) o;
		
		if (road != null ? !road.equals(address.road) : address.road != null) return false;
		if (state != null ? !state.equals(address.state) : address.state != null) return false;
		if (city != null ? !city.equals(address.city) : address.city != null) return false;
		if (zio != null ? !zio.equals(address.zio) : address.zio != null) return false;
		return houseNumber != null ? houseNumber.equals(address.houseNumber) : address.houseNumber == null;
	}
	
	@Override
	public int hashCode() {
		int result = road != null ? road.hashCode() : 0;
		result = 31 * result + (state != null ? state.hashCode() : 0);
		result = 31 * result + (city != null ? city.hashCode() : 0);
		result = 31 * result + (zio != null ? zio.hashCode() : 0);
		result = 31 * result + (houseNumber != null ? houseNumber.hashCode() : 0);
		return result;
	}
	
	@Override
	public String toString() {
		return "abc.Address{" +
				"road='" + road + '\'' +
				", state='" + state + '\'' +
				", city='" + city + '\'' +
				", zio='" + zio + '\'' +
				", houseNumber='" + houseNumber + '\'' +
				'}';
	}
	
	public String road() {
		return road;
	}
	
	public String state() {
		return state;
	}
	
	public String city() {
		return city;
	}
	
	public String zio() {
		return zio;
	}
	
	public String houseNumber() {
		return houseNumber;
	}
}

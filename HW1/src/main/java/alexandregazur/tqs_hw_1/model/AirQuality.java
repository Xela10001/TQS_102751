package alexandregazur.tqs_hw_1.model;


import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class AirQuality {

	private final float co;
	private final float no2;
	private final float o3;
	private final float so2;
	private final float pm2_5;
	private final float pm10;
	private final int us_epa_index;
	private final int gb_defra_index;
	
	public AirQuality(float co, float no2, float o3, float so2, float pm2_5, float pm10, int us_epa_index, int gb_defra_index) {
		this.co = co;
		this.no2 = no2;
		this.o3 = o3;
		this.so2 = so2;
		this.pm2_5 = pm2_5;
		this.pm10 = pm10;
		this.us_epa_index = us_epa_index;
		this.gb_defra_index = gb_defra_index;
	}
	
	public AirQuality(Map<String, Object> map) {
		this(
			(float) map.get("co"),
			(float) map.get("no2"),
			(float) map.get("o3"),
			(float) map.get("so2"),
			(float) map.get("pm2_5"),
			(float) map.get("pm10"),
			(int) map.get("us-epa-index"),
			(int) map.get("gb-defra-index")
		);
	}
	
	@Override
	public String toString() {
		return "AirQuality{" +
				"co=" + co +
				", no2=" + no2 +
				", o3=" + o3 +
				", so2=" + so2 +
				", pm2_5=" + pm2_5 +
				", pm10=" + pm10 +
				", us_epa_index=" + us_epa_index +
				", gb_defra_index=" + gb_defra_index +
				'}';
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		
		AirQuality that = (AirQuality) o;
		
		if (Float.compare(that.co, co) != 0) return false;
		if (Float.compare(that.no2, no2) != 0) return false;
		if (Float.compare(that.o3, o3) != 0) return false;
		if (Float.compare(that.so2, so2) != 0) return false;
		if (Float.compare(that.pm2_5, pm2_5) != 0) return false;
		if (Float.compare(that.pm10, pm10) != 0) return false;
		if (us_epa_index != that.us_epa_index) return false;
		return gb_defra_index == that.gb_defra_index;
	}
	
	@Override
	public int hashCode() {
		int result = (co != +0.0f ? Float.floatToIntBits(co) : 0);
		result = 31 * result + (no2 != +0.0f ? Float.floatToIntBits(no2) : 0);
		result = 31 * result + (o3 != +0.0f ? Float.floatToIntBits(o3) : 0);
		result = 31 * result + (so2 != +0.0f ? Float.floatToIntBits(so2) : 0);
		result = 31 * result + (pm2_5 != +0.0f ? Float.floatToIntBits(pm2_5) : 0);
		result = 31 * result + (pm10 != +0.0f ? Float.floatToIntBits(pm10) : 0);
		result = 31 * result + us_epa_index;
		result = 31 * result + gb_defra_index;
		return result;
	}
	
	public float getCo() {
		return co;
	}
	
	public float getNo2() {
		return no2;
	}
	
	public float getO3() {
		return o3;
	}
	
	public float getSo2() {
		return so2;
	}
	
	public float getPm2_5() {
		return pm2_5;
	}
	
	public float getPm10() {
		return pm10;
	}
	
	public int getUs_epa_index() {
		return us_epa_index;
	}
	
	public int getGb_defra_index() {
		return gb_defra_index;
	}
}


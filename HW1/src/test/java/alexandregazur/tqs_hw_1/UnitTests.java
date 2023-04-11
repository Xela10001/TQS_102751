package alexandregazur.tqs_hw_1;

import alexandregazur.tqs_hw_1.model.Cache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class UnitTests {
	
	@Test
	public void cacheTest() {
		Cache cache = new Cache();
		
		String city = "Lisbon";
		int days = 2;
		HashMap<String, Object> hashMap = new HashMap<>();
		
		Assertions.assertNull(cache.sampleCache(city));
		Assertions.assertEquals(cache.getRequests(), 1);
		Assertions.assertEquals(cache.getHits(), 0);
		Assertions.assertEquals(cache.getMisses(), 1);
		
		cache.putInCache(city, days, hashMap);
		
		Assertions.assertNull(cache.sampleCache(city));
		Assertions.assertEquals(cache.getRequests(), 2);
		Assertions.assertEquals(cache.getHits(), 0);
		Assertions.assertEquals(cache.getMisses(), 2);
		
		Assertions.assertEquals(cache.sampleCache(city, days), hashMap);
		Assertions.assertEquals(cache.getRequests(), 3);
		Assertions.assertEquals(cache.getHits(), 1);
		Assertions.assertEquals(cache.getMisses(), 2);
	}
}

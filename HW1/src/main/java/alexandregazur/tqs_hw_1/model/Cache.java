package alexandregazur.tqs_hw_1.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;
import java.util.Map;

public class Cache {
	@Value("${cache.ttl.seconds:30}")
	private Integer TTL = 30;
	private HashMap<CachedRequest, CachedResponse> cache;
	private int hits = 0;
	private int misses = 0;
	private int requests = 0;
	
	
	public Cache() {
		cache = new HashMap<>();
	}
	
	public Map<String, Object> sampleCache(String city, int days) {
		requests++;
		CachedResponse result = cache.getOrDefault(new CachedRequest(city, days), null);
		if(result == null) {
			misses++;
			return null;
		} else {
			hits++;
			result.refreshTTL(TTL);
			return result.getResponse();
		}
	}
	
	public Map<String, Object> sampleCache(String city) {
		return sampleCache(city, 0);
	}
	
	public void putInCache(String city, Map<String, Object> response) {
		putInCache(city, 0, response);
	}
	
	public void putInCache(String city, int days, Map<String, Object> response) {
		cache.putIfAbsent(new CachedRequest(city, days), new CachedResponse(response, TTL));
	}
	
	@Scheduled(fixedRateString = "1")
	private void removeExpiredElements() {
		for(CachedRequest cr : cache.keySet()) {
			CachedResponse response = cache.get(cr);
			if(response.isExpired()) {
				cache.remove(cr);
			}
		}
	}
	
	public int getHits() {
		return hits;
	}
	
	public int getMisses() {
		return misses;
	}
	
	public int getRequests() {
		return requests;
	}
	
	@Override
	public String toString() {
		return String.format("{\"requests:\"%d,\"hits:\"%d,\"misses:\"%d}", requests, hits, misses);
	}
}

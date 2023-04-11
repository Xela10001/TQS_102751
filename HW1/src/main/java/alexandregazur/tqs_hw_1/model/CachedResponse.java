package alexandregazur.tqs_hw_1.model;

import java.util.Map;

public class CachedResponse {
	private Map<String, Object> response;
	private long expirationDate;
	
	public CachedResponse(Map<String, Object> response, int TTLseconds) {
		this.response = response;
		this.expirationDate = System.currentTimeMillis() + TTLseconds*1000L;
	}
	
	public boolean isExpired() {
		return expirationDate < System.currentTimeMillis();
	}
	
	public void refreshTTL(int TTLseconds) {
		this.expirationDate = System.currentTimeMillis() + TTLseconds*1000L;
	}
	
	public Map<String, Object> getResponse() {
		return response;
	}
	
}

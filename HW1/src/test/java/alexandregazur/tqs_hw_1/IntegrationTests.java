package alexandregazur.tqs_hw_1;


import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void shouldIncludeCorrectRegionAndHaveCorrectCacheStatsTest() throws Exception {
		this.mockMvc.perform(get("/air-quality/Lisbon/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("{\"city\":\"Lisbon\",\"region\":\"Lisboa\",\"country\":\"Portugal\"")));
		this.mockMvc.perform(get("/cache")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("{\"requests:\"1,\"hits:\"0,\"misses:\"1}")));
		this.mockMvc.perform(get("/air-quality/Lisbon/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("{\"city\":\"Lisbon\",\"region\":\"Lisboa\",\"country\":\"Portugal\"")));
		this.mockMvc.perform(get("/cache")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("{\"requests:\"2,\"hits:\"1,\"misses:\"1}")));
	}

}

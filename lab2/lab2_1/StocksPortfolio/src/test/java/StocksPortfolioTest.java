import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StocksPortfolioTest {
	@Mock
	IStockmarketService stockMarket;
	
	StocksPortfolio stocksPortfolio;
	
	@BeforeEach
	public void startMarket() {
		stocksPortfolio = new StocksPortfolio(stockMarket);
		
		when(stockMarket.lookUpPrice(anyString())).thenReturn(5d);
		when(stockMarket.lookUpPrice("Apple")).thenReturn(125d);
	}
	
	@AfterEach
	public void tearDownMarket() {
		stockMarket = null;
		stocksPortfolio = null;
	}
	
	@Test
	public void testGetTotalValue() {
		stocksPortfolio.addStock(new Stock("Babel", 25));
		stocksPortfolio.addStock(new Stock("Apple", 75));
		assertThat(stocksPortfolio.getTotalValue(), is(9500d));
		verify(stockMarket, atLeast(2)).lookUpPrice(anyString());
	}
	
}

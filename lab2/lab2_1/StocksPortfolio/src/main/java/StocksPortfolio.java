import java.util.ArrayList;
import java.util.List;

public class StocksPortfolio {
	private List<Stock> stocks;
	private IStockmarketService stockmarket;
	
	public StocksPortfolio(IStockmarketService stockmarket) {
		stocks = new ArrayList<Stock>();
		this.stockmarket = stockmarket;
	}
	
	public void addStock(Stock stock) {
		stocks.add(stock);
	}
	
	public double getTotalValue() {
		double totalValue = 0;
		for(Stock stock : stocks) {
			totalValue += stock.getQuantity() * stockmarket.lookUpPrice(stock.getLabel());
		}
		return totalValue;
	}
}

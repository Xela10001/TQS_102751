package test;

import test.pages.ConfirmationPage;
import test.pages.HomePage;
import test.pages.PurchasePage;
import test.pages.ReservePage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class weiojpfTest {
	
	private HtmlUnitDriver driver;
	private HomePage homePage;
	private ReservePage reservePage;
	private PurchasePage purchasePage;
	private ConfirmationPage confirmationPage;
	
	@BeforeEach
	public void initializeDriver() {
		this.driver = new HtmlUnitDriver();
		driver.get("https://blazedemo.com/");
		driver.manage().window().setSize(new Dimension(1528, 794));
		homePage = new HomePage(driver);
		reservePage = new ReservePage(driver);
		purchasePage = new PurchasePage(driver);
		confirmationPage = new ConfirmationPage(driver);
	}
	
	@AfterEach
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
	
	@Test
	public void blazerunner() {
		homePage.selectDepartureCity("Portland");
		homePage.selectDestinationCity("Berlin");
		homePage.clickFindFlights();
		
		reservePage.chooseFlight();
		
		purchasePage.fillOutInfo("dajnoh", "exmp", "adsa", "wadwa", "1234", "0", "2025", "dajnoh ho ho");
		
		purchasePage.clickRememberMe();
		purchasePage.clickPurchaseFlight();
		
		assertThat(confirmationPage.getPrice()).isEqualTo("555 USD");
		assertThat(confirmationPage.getPageTitle()).isEqualTo("BlazeDemo Confirmation");
	}
}

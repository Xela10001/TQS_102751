package test.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    private final WebDriver driver;

    @FindBy(name = "fromPort")
    private WebElement fromPort1;

    @FindBy(name = "toPort")
    private WebElement toPort1;

    @FindBy(css = ".btn-primary")
    private WebElement findFlightsBttn;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void selectDepartureCity(String fromPort) {
        fromPort1.click();
        driver.findElement(By.xpath("//option[. = '" + fromPort + "']")).click();
    }

    public void selectDestinationCity(String toPort) {
        toPort1.click();
        driver.findElement(By.xpath("//option[. = '" + toPort + "']")).click();
    }

    public void clickFindFlights() {
        findFlightsBttn.click();
    }
}

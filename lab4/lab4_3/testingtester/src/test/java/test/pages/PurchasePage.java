package test.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PurchasePage {

    @FindBy(id = "inputName")
    private WebElement inputNameField;

    @FindBy(id = "address")
    private WebElement addressField;

    @FindBy(id = "city")
    private WebElement cityField;

    @FindBy(id = "state")
    private WebElement stateField;

    @FindBy(id = "zipCode")
    private WebElement zipCodeField;

    @FindBy(id = "creditCardNumber")
    private WebElement creditCardNumberField;

    @FindBy(id = "creditCardYear")
    private WebElement creditCardYearField;

    @FindBy(id = "nameOnCard")
    private WebElement nameOnCardField;

    @FindBy(css = ".checkbox")
    private WebElement rememberMeBox;

    @FindBy(css = ".btn-primary")
    private WebElement purchaseFlightBttn;

    public PurchasePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void fillOutInfo(String name, String address, String city, String state, String zipCode, String creditCardNumber, String creditCardYear, String nameOnCard) {
        stateField.sendKeys(state);
        inputNameField.sendKeys(name);
        cityField.sendKeys(city);
        zipCodeField.sendKeys(zipCode);
        addressField.sendKeys(address);
        
        creditCardNumberField.sendKeys(creditCardNumber);
        creditCardYearField.sendKeys(creditCardYear);
        nameOnCardField.sendKeys(nameOnCard);
    
    }

    public void clickRememberMe() {
        rememberMeBox.click();
    }

    public void clickPurchaseFlight() {
        purchaseFlightBttn.click();
    }
}
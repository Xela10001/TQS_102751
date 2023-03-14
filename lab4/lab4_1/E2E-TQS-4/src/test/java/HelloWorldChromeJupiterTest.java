import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.slf4j.Logger;

class HelloWorldChromeJupiterTest {
    
    static final Logger log = getLogger(lookup().lookupClass());
    private WebDriver driver;
    
    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }
    
    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
    }
    
    @Test
    void test() {
        // Exercise
        String sutUrl = "https://bonigarcia.dev/selenium-webdriver-java/";
        driver.get(sutUrl);
        String title = driver.getTitle();
        
        // Verify
        assertThat(title).isEqualTo("Hands-On Selenium WebDriver with Java");
    
        log.debug("The title of {} is {}", sutUrl, title);
    }
    
    @AfterEach
    void teardown() {
        driver.quit();
    }
    
}
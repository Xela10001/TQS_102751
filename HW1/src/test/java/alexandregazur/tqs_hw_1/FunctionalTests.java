package alexandregazur.tqs_hw_1;


import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class FunctionalTests {
	// For some reason, Selenium refused to work
	private ChromeDriver driver;
	
	private static ConfigurableApplicationContext app;
	
	@BeforeAll
	public static void startSpringBootApplication() {
		app = SpringApplication.run(TqsHw1Application.class);
	}
	
	@AfterAll
	public static void stopSpringBootApplication() {
		app.stop();
	}
	
	@BeforeEach
	public void initializeDriver() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		
		driver = new ChromeDriver(options);
		driver.get("http://localhost:3000/");
		driver.manage().window().setSize(new Dimension(2048, 1024));
	}
	
	@Test
	void webTest() {
		/*
		Running this produces this error:
		java.lang.IllegalStateException: The path to the driver executable The path to the driver executable must be set by the webdriver.chrome.driver system property; for more information, see https://chromedriver.chromium.org/. The latest version can be downloaded from https://chromedriver.chromium.org/downloads
		 */
	}
}

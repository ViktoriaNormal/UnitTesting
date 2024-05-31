package PageObject;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

abstract public class DriverStart  {

    public static WebDriver driver;
    private static ChromeOptions chromeOptions;
    private static WebDriverWait wait;

    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        chromeOptions = new ChromeOptions();
        WebDriver chromeDriver = new ChromeDriver(chromeOptions);
        driver = new EventFiringDecorator(new LogDriverActions()).decorate(chromeDriver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        driver.manage().window().maximize();
    }

    @BeforeEach
    public void initBefore() {
        setUp();
    }

    public void waitElements(List<WebElement> elements) {
        wait.until(visibilityOfAllElements(elements));
    }
}

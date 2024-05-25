package PageObject;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

abstract public class DriverStart  {

    public static WebDriver _driver;
    public static ChromeOptions _chromeOptions;
    public static WebDriverWait _wait;
    public String _urlGoogle = "https://www.google.com/";
    public String _urlYandex = "https://www.ya.ru/";
    public String _login = "13579";

    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        _chromeOptions = new ChromeOptions();
        WebDriver chromeDriver = new ChromeDriver(_chromeOptions);
        _driver = new EventFiringDecorator(new LogDriverActions()).decorate(chromeDriver);
        _wait = new WebDriverWait(_driver, Duration.ofSeconds(25));
        _driver.manage().window().maximize();
    }

    @BeforeEach
    public void initBefore() {
        setUp();
    }

    public void WaitElement(String path) {
        _wait.until(visibilityOfElementLocated(By.xpath(path)));
    }
}

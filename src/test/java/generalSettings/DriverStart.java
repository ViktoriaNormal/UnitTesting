package generalSettings;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.JavascriptExecutor;
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
    protected static JavascriptExecutor js;

    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--user-data-dir=C:\\Users\\Виктория\\AppData\\Local\\Google\\Chrome\\User Data\\");
        chromeOptions.addArguments("--profile-directory=Profile 1");
        WebDriver chromeDriver = new ChromeDriver(chromeOptions);
        driver = new EventFiringDecorator(new LogDriverActions()).decorate(chromeDriver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        js = (JavascriptExecutor) driver;
    }

    @BeforeEach
    public void initBefore() {
        setUp();
    }

    public void waitElement(WebElement element) {
        wait.until(visibilityOf(element));
    }

    public void waitElements(List<WebElement> elements) {
        wait.until(visibilityOfAllElements(elements));
    }
}

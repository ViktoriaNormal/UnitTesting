package reqresTest;

import generalSettings.DriverStart;
import generalSettings.LogDriverActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class ReqresPage {

    public String reqresURL = "https://reqres.in/";

    @FindBy(xpath = "//div[@class='endpoints']/ul")
    public WebElement requestButtonsList;

    @FindBy(xpath = "//span[contains(@class, 'response-code')]")
    public WebElement responseCode;

    @FindBy(xpath = "//pre[@data-key='output-response']")
    public WebElement output;

    private final Logger logger = LoggerFactory.getLogger(ReqresFrontTests.class);

    public ReqresPage(WebDriver driver) {
        DriverStart.driver = new EventFiringDecorator(new LogDriverActions()).decorate(driver);
        PageFactory.initElements(DriverStart.driver, this);
    }

    public WebElement findRequestButtonByDataId(String dataId) {
        return requestButtonsList.findElement(By.xpath(".//li[@data-id='" + dataId + "']"));
    }

    public boolean normalDelayForWebResponse() {
        WebDriverWait wait = new WebDriverWait(DriverStart.driver, Duration.ofSeconds(4));;
        wait.until(visibilityOf(output));
        return true;
    }

    public String getFormatWebResponse() {
        String response = output.getText().strip();

        if(response.equals("{}"))
            response = "{\n" + "    \n" + "}";

        return response;
    }
}

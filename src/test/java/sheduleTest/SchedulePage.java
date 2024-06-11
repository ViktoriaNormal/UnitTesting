package sheduleTest;

import generalSettings.DriverStart;
import generalSettings.LogDriverActions;
import letuTest.LetuTests;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SchedulePage {

    public String scheduleURL = "https://mospolytech.ru/";

    @FindBy(xpath = "//a[@href='/obuchauschimsya/raspisaniya/']")
    public WebElement scheduleButton;

    @FindBy(xpath = "//a[@href='https://rasp.dmami.ru/']")
    public WebElement buttonWatchOnSite;

    @FindBy(xpath = "//input[@placeholder='группа ...']")
    public WebElement groupInputField;

    @FindBy(xpath = "//input[@placeholder='группа ДПО ...']")
    public WebElement groupDPOInputField;

    @FindBy(xpath = " //div[@class='col-xs-6 col-sm-4 col-md-3 col-lg-2 group']")
    public WebElement groupButton;

    @FindBy(xpath = "//div[@class='found-groups row not-print']/child::*")
    public List<WebElement> groups;

    @FindBy(xpath="//div[@class='schedule-week']/child::*")
    public List<WebElement> daysOfWeek;

    private final Logger logger = LoggerFactory.getLogger(LetuTests.class);

    public SchedulePage(WebDriver driver) {
        DriverStart.driver = new EventFiringDecorator(new LogDriverActions()).decorate(driver);
        PageFactory.initElements(DriverStart.driver, this);
    }

    public boolean checkToday() {
        String dayOfWeek = LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE"));
        logger.info("Текущий день недели: {}", dayOfWeek);

        for (WebElement day : daysOfWeek) {
            if(dayOfWeek.equals("воскресенье")) {
                if(day.getAttribute("class").contains("schedule-day_today"))
                    return false;
            }
            else {
                if(day.getAttribute("class").contains("schedule-day_today"))
                    return true;
            }
        }
        return false;
    }
}

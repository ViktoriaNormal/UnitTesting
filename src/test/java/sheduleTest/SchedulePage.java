package sheduleTest;

import PageObject.DriverStart;
import PageObject.LogDriverActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringDecorator;

import java.util.List;

public class SchedulePage  extends DriverStart {

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

    public SchedulePage(WebDriver driver) {
        DriverStart.driver = new EventFiringDecorator(new LogDriverActions()).decorate(driver);
        PageFactory.initElements(DriverStart.driver, this);
    }

    public boolean checkToday() {
        for (WebElement day : daysOfWeek) {
            if(day.getAttribute("class").contains("schedule-day_today"))
                return true;
        }
        return false;
    }
}

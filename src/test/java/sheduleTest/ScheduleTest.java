package sheduleTest;

import generalSettings.DriverStart;
import generalSettings.TestListener;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@Feature("Тест страницы расписания Московского политеха")
@ExtendWith(TestListener.class)
public class ScheduleTest extends DriverStart {

    SchedulePage schedulePage;

    @Owner("Почтова Виктория")
    @DisplayName("Тестирование страницы расписания на сайте Мосполитеха")
    @Test
    public void scheduleTest() {
        schedulePage = new SchedulePage(driver);
        driver.get(schedulePage.scheduleURL);
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();

        clickSchedule();
        clickWatchOnSite();
        inputGroup();
        clickResultGroup();
    }

    @Step("Нажать на кнопку Расписания")
    public void clickSchedule() {
        schedulePage.scheduleButton.click();
    }

    @Step("В разделе “Расписания занятий” нажать \"Смотрите на сайте\"")
    public void clickWatchOnSite() {
        schedulePage.buttonWatchOnSite.click();

        String originalWindow = driver.getWindowHandle();

        for (String windowHandle : driver.getWindowHandles()) {
            if(!originalWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        int windowNumber = driver.getWindowHandles().size();
        Assertions.assertEquals(2, windowNumber);
    }

    @Step("В разделе \"Расписания занятий” нажать “Смотрите на сайте\"")
    public void inputGroup() {
        String searchGroup = "221-361";
        schedulePage.groupInputField.sendKeys(searchGroup);

        waitElements(schedulePage.groups);
        int size = schedulePage.groups.size();
        Assertions.assertEquals(1, size);

        String resultGroup = schedulePage.groupButton.getText();
        Assertions.assertEquals(searchGroup, resultGroup);
    }

    @Step("Нажать на найденную группу в результатах поиска")
    public void clickResultGroup() {
        schedulePage.groupButton.click();

        String searchGroup = schedulePage.groupInputField.getText();
        String choiceGroup = schedulePage.groupDPOInputField.getText();
        Assertions.assertEquals(searchGroup, choiceGroup);

        waitElements(schedulePage.daysOfWeek);
        Assertions.assertTrue(schedulePage.checkToday());
    }
}

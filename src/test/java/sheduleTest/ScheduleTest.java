package sheduleTest;

import generalSettings.DriverStart;
import generalSettings.TestListener;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import letuTest.LetuTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Feature("Тест страницы расписания Московского политеха")
@ExtendWith(TestListener.class)
public class ScheduleTest extends DriverStart {

    SchedulePage schedulePage;
    Logger logger = LoggerFactory.getLogger(LetuTest.class);

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
        logger.info("Нажата кнопка Расписания.");
    }

    @Step("В разделе “Расписания занятий” нажать \"Смотрите на сайте\"")
    public void clickWatchOnSite() {
        schedulePage.buttonWatchOnSite.click();
        logger.info("Нажата кнопка \"Смотрите на сайте\".");

        String originalWindow = driver.getWindowHandle();

        for (String windowHandle : driver.getWindowHandles()) {
            if(!originalWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        int windowNumber = driver.getWindowHandles().size();
        Assertions.assertEquals(2, windowNumber);
        logger.info("Страница поиска расписания открыта в новой вкладке. Проверка успешно пройдена!");

        driver.manage().deleteAllCookies();
        driver.navigate().refresh();
    }

    @Step("В разделе \"Расписания занятий” нажать “Смотрите на сайте\"")
    public void inputGroup() {
        String searchGroup = "234-221";
        schedulePage.groupInputField.sendKeys(searchGroup);
        logger.info("В поле поиска расписания группы введена группа 221-361.");

        waitElements(schedulePage.groups);
        int size = schedulePage.groups.size();
        Assertions.assertEquals(1, size);
        logger.info("В результатах поиска отобразилась только одна группа. Проверка успешно пройдена!");

        String resultGroup = schedulePage.groupButton.getText();
        Assertions.assertEquals(searchGroup, resultGroup);
        logger.info("В результатах поиска отобразилась только группа с искомым номером. Проверка успешно пройдена!");
    }

    @Step("Нажать на найденную группу в результатах поиска")
    public void clickResultGroup() {
        schedulePage.groupButton.click();
        logger.info("Выполнено нажатие на найденную группу в результатах поиска.");

        String searchGroup = schedulePage.groupInputField.getText();
        String choiceGroup = schedulePage.groupDPOInputField.getText();
        Assertions.assertEquals(searchGroup, choiceGroup);
        logger.info("Открылась страница расписания выбранной группы. Проверка пройдена успешно!");

        waitElements(schedulePage.daysOfWeek);
        Assertions.assertTrue(schedulePage.checkToday());
        logger.info("Проверка на выделение текущего дня недели цветом пройдена успешно!");
    }
}

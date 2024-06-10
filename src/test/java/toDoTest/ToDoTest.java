package toDoTest;

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

@Feature("Тест списка дел")
@ExtendWith(TestListener.class)
public class ToDoTest extends DriverStart {

    ToDoPage toDoPage;
    Logger logger = LoggerFactory.getLogger(LetuTest.class);

    @Owner("Почтова Виктория")
    @DisplayName(value="Тестирование списка дел \"LambdaTest Sample App\"")
    @Test
    public void toDoTest() {
        toDoPage = new ToDoPage(driver);
        driver.get(toDoPage.toDoURL);

        stepCheckTitle();
        stepCheckText();
        stepCheckFirstItem();
        stepCheckCheckbox();
        repeatingSteps();
        stepCheckAddingNewItem();
        stepCheckClickingNewItem();
    }

    @Step("Перейти по ссылке: https://lambdatest.github.io/sample-todo-app/")
    public void stepCheckTitle() {
        String pageTitle = toDoPage.pageTitle.getText();
        Assertions.assertEquals("LambdaTest Sample App", pageTitle);
        logger.info("Проверка на соответствие заголовка страницы прошла успешно!");
    }

    @Step("Проверить, что присутствует текст: \"5 of 5 remaining\"")
    public void stepCheckText() {
        String text = toDoPage.textRemaining.getText();
        Assertions.assertEquals("5 of 5 remaining", text);
        logger.info("Проверка на наличие текста \"5 of 5 remaining\" на странице прошла успешно!");
    }

    @Step("Проверить, что первый элемент списка не зачеркнут")
    public void stepCheckFirstItem() {
        String classFirstItem = toDoPage.findItemClass(0);
        Assertions.assertEquals("done-false", classFirstItem);
        logger.info("Первый элемент списка не зачёркунт. К нему применён класс \"done-false\". Проверка успешно пройдена!");
    }

    @Step("Поставить галочку у первого элемента")
    public void stepCheckCheckbox() {
        toDoPage.checkBoxClick(0);
        logger.info("Поставлена галочка у первого элемента.");

        String classFirstItem = toDoPage.findItemClass(0);
        Assertions.assertEquals("done-true", classFirstItem);
        logger.info("Первый элемент списка стал зачёркнутым. К нему применён класс \"done-true\". Проверка успешно пройдена!");

        String countOfElements = toDoPage.textRemaining.getText();
        Assertions.assertEquals("4 of 5 remaining", countOfElements);
        logger.info("Отображаемое число оставшихся элементов уменьшилось на 1. Проверка успешно пройдена!");
    }

    @Step("Повторить шаги 3, 4 для остальных элементов списка")
    public void repeatingSteps() {
        int listSize = toDoPage.toDoList.size();

        for(int i = 1; i < listSize; i++) {
            String classCurrentItem = toDoPage.findItemClass(i);
            Assertions.assertEquals("done-false", classCurrentItem);
            logger.info("{} элемент списка не зачёркнут. Класс \"done-false\" не применён. Проверка успешно пройдена!", i + 1);

            toDoPage.checkBoxClick(i);
            logger.info("Поставлена галочка у {} элемента.", i + 1);

            classCurrentItem = toDoPage.findItemClass(i);
            Assertions.assertEquals("done-true", classCurrentItem);
            logger.info("{} элемент списка стал зачёркнутым. К нему применён класс \"done-true\". Проверка успешно пройдена!", i + 1);

            String countOfElements = toDoPage.textRemaining.getText();
            int left = 5 - i - 1;
            Assertions.assertEquals(left + " of 5 remaining", countOfElements);
            logger.info("Отображаемое число оставшихся элементов уменьшилось на 1. Проверка успешно пройдена!");
        }
    }

    @Step("Добавить новый элемент списка")
    public void stepCheckAddingNewItem() {
        toDoPage.inputField.sendKeys("Sixth team");
        logger.info("Введено название нового элемента списка.");

        toDoPage.submitButton.click();
        logger.info("Добавлен новый элемент списка.");

        String classFirstItem = toDoPage.findItemClass(5);
        Assertions.assertEquals("done-false", classFirstItem);
        logger.info("Новый элемент списка не зачёркнут. Проверка пройдена успешно!");

        String countOfElements = toDoPage.textRemaining.getText();
        Assertions.assertEquals("1 of 6 remaining", countOfElements);
        logger.info("Отображаемое общее число и число оставшихся элементов увеличились на 1.");
    }

    @Step("Нажать на новый элемент списка")
    public void stepCheckClickingNewItem() {
        toDoPage.checkBoxClick(5);
        logger.info("На новый элемент списка поставлена галочка.");

        String classFirstItem = toDoPage.findItemClass(5);
        Assertions.assertEquals("done-true", classFirstItem);
        logger.info("Новый элемент списка стал зачеркнутым. Проверка успешно пройдена!");

        String countOfElements = toDoPage.textRemaining.getText();
        Assertions.assertEquals("0 of 6 remaining", countOfElements);
        logger.info("Отображаемое число оставшихся элементов уменьшилось на 1. Проверка успешно пройдена!");
    }
}

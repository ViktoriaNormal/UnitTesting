package toDoTest;

import generalSettings.DriverStart;
import generalSettings.TestListener;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@Feature("Тест списка дел")
@ExtendWith(TestListener.class)
public class ToDoTest extends DriverStart {
    //Добавить логирование шагов

    ToDoPage toDoPage;

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

    @Step("Шаг 1. Перейти по ссылке: https://lambdatest.github.io/sample-todo-app/")
    public void stepCheckTitle() {
        String pageTitle = toDoPage.pageTitle.getText();
        Assertions.assertEquals("LambdaTest Sample App", pageTitle);
    }

    @Step("Шаг 2. Проверить, что присутствует текст: \"5 of 5 remaining\"")
    public void stepCheckText() {
        String text = toDoPage.textRemaining.getText();
        Assertions.assertEquals("5 of 5 remaining", text);
    }

    @Step("Шаг 3. Проверить, что первый элемент списка не зачеркнут")
    public void stepCheckFirstItem() {
        String classFirstItem = toDoPage.findItemClass(0);
        Assertions.assertEquals("done-false", classFirstItem);
    }

    @Step("Шаг 4. Поставить галочку у первого элемента")
    public void stepCheckCheckbox() {
        toDoPage.checkBoxClick(0);

        String classFirstItem = toDoPage.findItemClass(0);
        Assertions.assertEquals("done-true", classFirstItem);

        String countOfElements = toDoPage.textRemaining.getText();
        Assertions.assertEquals("4 of 5 remaining", countOfElements);
    }

    @Step("Шаг 5. Повторить шаги 3, 4 для остальных элементов списка")
    public void repeatingSteps() {
        int listSize = toDoPage.toDoList.size();

        for(int i = 1; i < listSize; i++) {
            String classCurrentItem = toDoPage.findItemClass(i);
            Assertions.assertEquals("done-false", classCurrentItem);

            toDoPage.checkBoxClick(i);

            classCurrentItem = toDoPage.findItemClass(i);
            Assertions.assertEquals("done-true", classCurrentItem);

            String countOfElements = toDoPage.textRemaining.getText();
            int left = 5 - i - 1;
            Assertions.assertEquals(left + " of 5 remaining", countOfElements);
        }
    }

    @Step("Шаг 6. Добавить новый элемент списка")
    public void stepCheckAddingNewItem() {
        toDoPage.inputField.sendKeys("Sixth team");
        toDoPage.submitButton.click();

        String classFirstItem = toDoPage.findItemClass(5);
        Assertions.assertEquals("done-false", classFirstItem);

        String countOfElements = toDoPage.textRemaining.getText();
        Assertions.assertEquals("1 of 6 remaining", countOfElements);
    }

    @Step("Шаг 6. Нажать на новый элемент списка")
    public void stepCheckClickingNewItem() {
        toDoPage.checkBoxClick(5);

        String classFirstItem = toDoPage.findItemClass(5);
        Assertions.assertEquals("done-true", classFirstItem);

        String countOfElements = toDoPage.textRemaining.getText();
        Assertions.assertEquals("0 of 6 remaining", countOfElements);
    }

    //allure serve target/allure-results
}

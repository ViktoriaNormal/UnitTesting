package yandexMarketTest;

import generalSettings.DriverStart;
import generalSettings.TestListener;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;

@Feature("Тест Яндекс.Маркета")
@ExtendWith(TestListener.class)
public class YandexTest extends DriverStart {

    YandexPage yandexPage;

    @Owner("Почтова Виктория")
    @DisplayName(value = "Тестирование Яндекс.Маркета")
    @Test
    public void yandexTest() {
        yandexPage = new YandexPage(driver);
        driver.get(yandexPage.yandexURL);

        choiceCategory();
        printProductsTitleAndPrice();
        inputPrice();
    }

    @Step("Шаг 2. В меню “Каталог” выбрать категорию: Электроника -> Ноутбуки, планшеты и электронные книги -> Ноутбуки")
    public void choiceCategory() {
        waitElement(yandexPage.catalogButton);
        yandexPage.catalogButton.click();
        Actions action = new Actions(driver);

        waitElement(yandexPage.electronics);
        action.moveToElement(yandexPage.electronics).perform();
        waitElement(yandexPage.laptopsTabletsEbooks);
        action.moveToElement(yandexPage.laptopsTabletsEbooks).perform();
        yandexPage.laptops.click();
    }

    @Step("Шаг 3. Вывести в лог первые 5 найденных товаров (название и цену)")
    public void printProductsTitleAndPrice() {
        yandexPage.printTitleAndPrice();
    }

    @Step("Шаг 4. В меню фильтров заполнить поле “Цена, ₽” следующими значениями: от 60000 до 110000.")
    public void inputPrice() {
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();

        yandexPage.inputMinPrice.sendKeys("60000");
        yandexPage.inputMaxPrice.sendKeys("110000");

        boolean inRange = yandexPage.checkFilterPrice();
        Assertions.assertTrue(inRange);
    }

}

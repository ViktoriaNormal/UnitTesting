package yandexMarketTest;

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
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

@Feature("Тест Яндекс.Маркета")
@ExtendWith(TestListener.class)
public class YandexTest extends DriverStart {

    YandexPage yandexPage;
    Logger logger = LoggerFactory.getLogger(LetuTest.class);

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

    @Step("В меню “Каталог” выбрать категорию: Электроника -> Ноутбуки, планшеты и электронные книги -> Ноутбуки")
    public void choiceCategory() {
        waitElement(yandexPage.catalogButton);
        yandexPage.catalogButton.click();
        logger.info("Открыто меню \"Каталог\".");

        Actions action = new Actions(driver);

        waitElement(yandexPage.electronics);
        action.moveToElement(yandexPage.electronics).perform();
        logger.info("Осуществлено наведение на категорию \"Электроника\".");

        waitElement(yandexPage.laptops);
        yandexPage.laptops.click();
        logger.info("Нажата кнопка \"Ноутбуки\" для перехода в соответствующую подкатегорию.");
    }

    @Step("Вывести в лог первые 5 найденных товаров (название и цену)")
    public void printProductsTitleAndPrice() {
        yandexPage.printTitleAndPrice();
    }

    @Step("В меню фильтров заполнить поле “Цена, ₽” следующими значениями: от 60000 до 110000.")
    public void inputPrice() {
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();

        yandexPage.inputMinPrice.sendKeys("60000");
        logger.info("Поле \"от\" фильтра стоимости заполнено значением 60000.");

        yandexPage.inputMaxPrice.sendKeys("110000");
        logger.info("Поле \"до\" фильтра стоимости заполнено значением 110000.");

        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        boolean inRange = yandexPage.checkFilterPrice();
        Assertions.assertTrue(inRange);
        logger.info("В выведенном списке стоимость всех товаров находится в указанном диапазоне. Проверка успешно пройдена!");
    }
}

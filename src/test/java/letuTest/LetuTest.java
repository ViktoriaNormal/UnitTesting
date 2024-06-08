package letuTest;

import generalSettings.DriverStart;
import generalSettings.TestListener;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.concurrent.TimeUnit;

@Feature("Тест интернет-магазина \"Лэтуаль\"")
@ExtendWith(TestListener.class)
public class LetuTest extends DriverStart {

    LetuPage letuPage;

    @Owner("Почтова Виктория")
    @DisplayName(value="Тестирование фильтра брендов по букве начала названия и фильтра товаров \"Доступность\"")
    @Test
    public void brandTest() {
        letuPage = new LetuPage(driver);
        driver.get(letuPage.letuURL);

        clickBrands();
        clickA();
        clickARESA();
        choiceAvailable();
        printProducts();
    }

    @Step("Шаг 2. В верхнем меню нажать на кнопку \"Бренды\"")
    public void clickBrands() {
        js.executeScript("arguments[0].click();", letuPage.brandsLink);
    }

    @Step("Шаг 3. В фильтрах нажать на букву \"A\"")
    public void clickA() {
        js.executeScript("arguments[0].click();", letuPage.filterBrandsA);
        Assertions.assertTrue(letuPage.checkFilterByLetter());
    }

    @Step("Шаг 4. Нажать на бренд с названием \"ARESA\"")
    public void clickARESA() {
        js.executeScript("arguments[0].click();", letuPage.findBrand("ARESA"));
    }

    @Step("Шаг 5. В фильтрах в категории «Доступность» выбрать \"Есть в наличии\"")
    public void choiceAvailable() {
        js.executeScript("arguments[0].click();", letuPage.filterAvailability);
        js.executeScript("arguments[0].click();", letuPage.checkboxAvailability);
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(letuPage.checkAvailability());
    }

    @Step("Шаг 6. . Вывести в лог первые 10 отображаемых товаров (названия и цены)")
    public void printProducts() {
        letuPage.printProductsByQuantity(10);
    }

//    @Owner("Почтова Виктория")
//    @DisplayName(value="Тестирование сортировки \"По возрастанию цены\"")
//    @Test
//    public void sortTest() {
//        letuPage = new LetuPage(driver);
//        driver.get(letuPage.letuURL);
//
//
//    }
//
//    @Step("Шаг 2. В меню \"Каталог\" выбрать категорию товаров: Уход за кожей -> Средства для ухода за лицом -> Средства для умывания")
//    public void () {}
//
//    @Step("Шаг 3. Вывести в лог первые 10 отображаемых товаров (названия и цены)")
//    public void () {}
//
//    @Step("Шаг 4. В сортировке выбрать \"По возрастанию цены\"")
//    public void () {}
//
//    @Owner("Почтова Виктория")
//    @DisplayName(value="Тестирование поисковой строки, фильтра \"Стоимость\" и добавления товара в корзину")
//    @Test
//    public void searchFilterCartTest() {
//        letuPage = new LetuPage(driver);
//        driver.get(letuPage.letuURL);
//
//
//    }
//
//    @Step("Шаг 2. В поисковую строку ввести запрос \"шампунь для волос\" и нажать на кнопку \"Найти\"")
//    public void () {}
//
//    @Step("Шаг 3. В фильтре \"Стоимость\" установить диапазон цен товаров: от 500 до 1500")
//    public void () {}
//
//    @Step("Шаг 4. Запомнить первые 4 найденных товара")
//    public void () {}
//
//    @Step("Шаг 5. Вывести в лог первые 4 найденных товара (названия и цены)")
//    public void () {}
//
//    @Step("Шаг 6. Добавить в корзину первые 4 найденных товара")
//    public void () {}
//
//    @Step("Шаг 7. Нажать на кнопку \"Корзина\" в верхнем меню")
//    public void () {}
//
//    @Step("Шаг 8. Вывести в лог добавленные в корзину товары (названия и цены)")
//    public void () {}

}

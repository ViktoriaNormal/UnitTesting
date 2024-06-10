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
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Feature("Тест интернет-магазина \"Лэтуаль\"")
@ExtendWith(TestListener.class)
public class LetuTest extends DriverStart {

    LetuPage letuPage;
    Actions action;
    List<WebElement> rememberProducts;

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

    @Step("В верхнем меню нажать на кнопку \"Бренды\"")
    public void clickBrands() {
        js.executeScript("arguments[0].click();", letuPage.brandsLink);
    }

    @Step("В фильтрах нажать на букву \"A\"")
    public void clickA() {
        js.executeScript("arguments[0].click();", letuPage.filterBrandsA);
        Assertions.assertTrue(letuPage.checkFilterByLetter());
    }

    @Step("Нажать на бренд с названием \"ARESA\"")
    public void clickARESA() {
        js.executeScript("arguments[0].click();", letuPage.findBrand("ARESA"));
    }

    @Step("В фильтрах в категории «Доступность» выбрать \"Есть в наличии\"")
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

    @Step("Вывести в лог первые 10 отображаемых товаров (названия и цены)")
    public void printProducts() {
        letuPage.printProductsByQuantity(10);
    }

    @Owner("Почтова Виктория")
    @DisplayName(value="Тестирование сортировки \"По возрастанию цены\"")
    @Test
    public void sortTest() {
        letuPage = new LetuPage(driver);
        driver.get(letuPage.letuURL);

        choiceCategory();
        printProducts();
        choiceSortByIncreasing();
    }

    @Step("В меню \"Каталог\" выбрать категорию товаров: Уход за кожей -> Средства для ухода за лицом -> Средства для умывания")
    public void choiceCategory() {
        js.executeScript("arguments[0].click();", letuPage.catalogButton);

        action.moveToElement(letuPage.category).perform();

        js.executeScript("arguments[0].click();", letuPage.subcategoryCleanser);
    }

    @Step("В сортировке выбрать \"По возрастанию цены\"")
    public void choiceSortByIncreasing() {
        js.executeScript("arguments[0].click();", letuPage.sortButton);
        js.executeScript("arguments[0].click();", letuPage.sortByIncreasing);

        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertTrue(letuPage.checkSort());
    }

    @Owner("Почтова Виктория")
    @DisplayName(value="Тестирование поисковой строки, фильтра \"Стоимость\" и добавления товара в корзину")
    @Test
    public void searchFilterCartTest() {
        letuPage = new LetuPage(driver);
        driver.get(letuPage.letuURL);
        action = new Actions(driver);

        inputRequest();
        inputPrice();
        rememberProducts();
        printFoundProducts();
        addToCart();
        goToCart();
        printCartProducts();
        resetTest();
    }

    @Step("В поисковую строку ввести запрос \"шампунь для волос\" и нажать на кнопку \"Найти\"")
    public void inputRequest() {
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        action.sendKeys(letuPage.searchInput, "шампунь для волос").perform();

        js.executeScript("arguments[0].click();", letuPage.searchButton);

        Assertions.assertTrue(letuPage.checkRequestProducts());
    }

    @Step("В фильтре \"Стоимость\" установить диапазон цен товаров: от 500 до 1500")
    public void inputPrice() {
        action.click(letuPage.costFilter).perform();
        action.sendKeys(letuPage.inputMinPrice, "500").perform();
        action.sendKeys(letuPage.inputMaxPrice, "1500").perform();
        action.sendKeys(Keys.ENTER).perform();

        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertTrue(letuPage.checkCostFilter());
    }

    @Step("Запомнить первые 4 найденных товара")
    public void rememberProducts() {
        rememberProducts = letuPage.rememberProducts();
    }

    @Step("Вывести в лог первые 4 найденных товара (названия и цены)")
    public void printFoundProducts() {
        letuPage.printProductsByQuantity(4);
    }

    @Step("Добавить в корзину первые 4 найденных товара")
    public void addToCart() {
        for(int i = 0; i < 4; i++) {
            WebElement firstCartButton = letuPage.products.get(i).findElement(By.xpath(".//button[@data-at-add-to-cart-button]"));

            js.executeScript("arguments[0].click()", firstCartButton);

            if(!letuPage.cartModalWindow.isEmpty()) {
                js.executeScript("arguments[0].click()", letuPage.modalCartButton);
                js.executeScript("arguments[0].click()", letuPage.buttonClose);
            }
        }

        Assertions.assertTrue(letuPage.checkChangeButtonText());
    }

    @Step("Нажать на кнопку \"Корзина\" в верхнем меню")
    public void goToCart() {
        js.executeScript("arguments[0].click();", letuPage.cartButton);

        Assertions.assertTrue(letuPage.compareProducts(rememberProducts));
    }

    @Step("Вывести в лог добавленные в корзину товары (названия и цены)")
    public void printCartProducts() {
        letuPage.printCartProducts();
    }

    public void resetTest() {
        js.executeScript("arguments[0].click()", letuPage.deleteButton);
        js.executeScript("arguments[0].click()", letuPage.confirmDeleteButton);
    }
}

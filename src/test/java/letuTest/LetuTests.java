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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Feature("Тест интернет-магазина \"Лэтуаль\"")
@ExtendWith(TestListener.class)
public class LetuTests extends DriverStart {

    LetuPage letuPage;
    Actions action;
    List<WebElement> rememberProducts;
    Logger logger = LoggerFactory.getLogger(LetuTests.class);

    @Owner("Почтова Виктория")
    @DisplayName(value = "Тестирование фильтра брендов по букве начала названия и фильтра товаров \"Доступность\"")
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
        logger.info("Нажата кнопка \"Бренды\".");
    }

    @Step("В фильтрах нажать на букву \"A\"")
    public void clickA() {
        js.executeScript("arguments[0].click();", letuPage.filterBrandsA);
        logger.info("Нажата буква \"A\".");

        Assertions.assertTrue(letuPage.checkFilterByLetter());
        logger.info("Все отображаемые бренды начинаются с буквы «A». Проверка успешно пройдена!");
    }

    @Step("Нажать на бренд с названием \"ARESA\"")
    public void clickARESA() {
        js.executeScript("arguments[0].click();", letuPage.findBrand("ARESA"));
        logger.info("Нажата кнопка бренда \"ARESA\".");
    }

    @Step("В фильтрах в категории \"Доступность\" выбрать \"Есть в наличии\"")
    public void choiceAvailable() {
        js.executeScript("arguments[0].click();", letuPage.filterAvailability);
        logger.info("Нажата кнопка фильра \"Доступность\".");

        js.executeScript("arguments[0].click();", letuPage.checkboxAvailability);
        logger.info("В фильтре \"Доступность\" выбран пункт \"Есть в наличии\".");

        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertTrue(letuPage.checkAvailability());
        logger.info("На странице открытого бренда отображаются только те товары, которые есть в наличии. Проверка успешно пройдена!");
    }

    @Step("Вывести в лог первые 10 отображаемых товаров (названия и цены)")
    public void printProducts() {
        letuPage.printProductsByQuantity(10);
    }

    @Owner("Почтова Виктория")
    @DisplayName(value = "Тестирование сортировки \"По возрастанию цены\"")
    @Test
    public void sortTest() {
        letuPage = new LetuPage(driver);
        driver.get(letuPage.letuURL);
        action = new Actions(driver);

        choiceCategory();
        printProducts();
        choiceSortByIncreasing();
    }

    @Step("В меню \"Каталог\" выбрать категорию товаров: Уход за кожей -> Средства для ухода за лицом -> Средства для умывания")
    public void choiceCategory() {
        js.executeScript("arguments[0].click();", letuPage.catalogButton);
        logger.info("Открыто  меню \"Каталог\".");

        action.moveToElement(letuPage.category).perform();
        logger.info("Выполнено наведение на категорию \"Уход за кожей\".");

        js.executeScript("arguments[0].click();", letuPage.subcategoryCleanser);
        logger.info("Нажата кнопка \"Средства для умывания\".");
    }

    @Step("В сортировке выбрать \"По возрастанию цены\"")
    public void choiceSortByIncreasing() {
        js.executeScript("arguments[0].click();", letuPage.sortButton);
        logger.info("Нажата кнопка сортировки товаров.");

        js.executeScript("arguments[0].click();", letuPage.sortByIncreasing);
        logger.info("Выбрана сортировка \"По возрастанию цены\".");

        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertTrue(letuPage.checkSort());
        logger.info("Товары отображаются в порядке возрастания цены (каждый последующий больше или равен предыдущему). Проверка успешно пройдена!");
    }

    @Owner("Почтова Виктория")
    @DisplayName(value = "Тестирование поисковой строки, фильтра \"Стоимость\" и добавления товара в корзину")
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
        logger.info("В поисковую строку введён запрос \"шампунь для волос\".");

        js.executeScript("arguments[0].click();", letuPage.searchButton);
        logger.info("Нажата кнопка поиска.");

        js.executeScript("window.scrollBy(0,1000)");
        logger.info("Осуществлён скролл страницы.");

        Assertions.assertTrue(letuPage.checkRequestProducts());
        logger.info("Отображаемые товары содержат в кратком описании на карточке слово \"шампунь\". Проверка успешно пройдена!");
    }

    @Step("В фильтре \"Стоимость\" установить диапазон цен товаров: от 500 до 1500")
    public void inputPrice() {
        js.executeScript("window.scrollBy(0,5000)");
        logger.info("Осуществлён скролл страницы.");

        action.click(letuPage.costFilter).perform();
        logger.info("Нажата кнопка фильтра \"Стоимость\".");

        action.sendKeys(letuPage.inputMinPrice, "500").perform();
        logger.info("Введена минимальная сумма: 500.");

        action.sendKeys(letuPage.inputMaxPrice, "1500").perform();
        logger.info("Введена максимальная сумма: 1500.");

        action.sendKeys(Keys.ENTER).perform();
        logger.info("Фильтры применены.");

        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertTrue(letuPage.checkCostFilter());
        logger.info("Все отображаемые товары имеют цены, находящиеся в диапазоне от 500 до 1500. Проверка успешно пройдена!");
    }

    @Step("Запомнить первые 4 найденных товара")
    public void rememberProducts() {
        rememberProducts = letuPage.rememberProducts();
        logger.info("Первые 4 найденных товара сохранены.");
    }

    @Step("Вывести в лог первые 4 найденных товара (названия и цены)")
    public void printFoundProducts() {
        letuPage.printProductsByQuantity(4);
    }

    @Step("Добавить в корзину первые 4 найденных товара")
    public void addToCart() {
        for (int i = 0; i < 4; i++) {
            WebElement firstCartButton = letuPage.products.get(i).findElement(By.xpath(".//button[@data-at-add-to-cart-button]"));

            js.executeScript("arguments[0].click()", firstCartButton);
            logger.info("Нажата кнопка \"В корзину\" на карточке {} товара.", i + 1);

            if (!letuPage.cartModalWindow.isEmpty()) {
                js.executeScript("arguments[0].click()", letuPage.modalCartButton);
                logger.info("Нажата кнопка \"В корзину\" на модальном окне {} товара.", i + 1);

                js.executeScript("arguments[0].click()", letuPage.buttonClose);
                logger.info("Модальное окно {} товара закрыто.", i + 1);
            }
        }

        Assertions.assertTrue(letuPage.checkChangeButtonText());
        logger.info("На карточках добавленных товаров название кнопки \"В корзину\" изменилось на \"В корзине\". Проверка успешно пройдена!");
    }

    @Step("Нажать на кнопку \"Корзина\" в верхнем меню")
    public void goToCart() {
        js.executeScript("arguments[0].click();", letuPage.cartButton);
        logger.info("Осуществлён переход на страницу \"Корзина\".");

        Assertions.assertTrue(letuPage.compareProducts(rememberProducts));
        logger.info("На странице \"Корзина\" присутствуют запомненные ранее товары (совпадают названия и цены). Проверка успешно пройдена!");
    }

    @Step("Вывести в лог добавленные в корзину товары (названия и цены)")
    public void printCartProducts() {
        letuPage.printCartProducts();
    }

    public void resetTest() {
        js.executeScript("arguments[0].click()", letuPage.deleteButton);
        logger.info("Нажата кнопка удаления выбранных товаров из корзины.");

        js.executeScript("arguments[0].click()", letuPage.confirmDeleteButton);
        logger.info("Нажата кнопка удаления выбранных товаров в модальном окне подтверждения операции удаления.");

        logger.info("Корзина успешно очищена. Тест возвращён в исходное состояние.");
    }
}

package letuTest;

import generalSettings.DriverStart;
import generalSettings.LogDriverActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class LetuPage extends DriverStart {

    public String letuURL = "https://www.letu.ru/";

    @FindBy(xpath = "//a[@href='/brands' and @class='header-v4-horizontal-menu__item']")
    public WebElement brandsLink;

    @FindBy(xpath = "//div[@class='brands-letters__item' and contains(text(), 'A')]")
    public WebElement filterBrandsA;

    @FindBy(xpath = "//div[@class='brand-item__link']/a")
    public List<WebElement> filterBrandsList;

    @FindBy(xpath = "//div[@id='filter-menu-6']/child::*/child::*")
    public WebElement filterAvailability;

    @FindBy(xpath = "//p[contains(text(), 'Есть в наличии')]/parent::*/preceding-sibling::*")
    public WebElement checkboxAvailability;

    @FindBy(xpath = "//a[@class='le-link product-tile__item-container']")
    public List<WebElement> products;

    @FindBy(xpath = "//button[contains(@class, 'header-v4-catalog__button')]")
    public WebElement catalogButton;

    @FindBy(xpath = "//a[contains(text(), 'Уход за кожей')]")
    public WebElement category;

    @FindBy(xpath = "//a[contains(text(), 'Средства для ухода за лицом')]")
    public WebElement subcategoryFacialCare;

    @FindBy(xpath = "//a[contains(text(), 'Средства для умывания')]")
    public WebElement subcategoryCleanser;

    @FindBy(xpath = "//button[contains(@class, 'products-list-filter-select-v4__options-btn')]")
    public WebElement sortButton;

    @FindBy(xpath = "//span[contains(text(), 'По возрастанию цены')]")
    public WebElement sortByIncreasing;

    @FindBy(xpath = "//input[@placeholder='Поиск']")
    public WebElement searchInput;

    @FindBy(xpath = "//button[@title='Поиск']")
    public WebElement searchButton;

    @FindBy(xpath = "//input[contains(@placeholder, 'от')]")
    public WebElement inputMinPrice;

    @FindBy(xpath = "//input[contains(@placeholder, 'до')]")
    public WebElement inputMaxPrice;

    @FindBy(xpath = "//a[@href='/cart']")
    public WebElement cartButton;

    @FindBy(xpath = "//div[@class='common-commerce-item__product']")
    public List<WebElement> cartProducts;

    private final Logger logger = LoggerFactory.getLogger(LetuTest.class);

    public LetuPage(WebDriver driver) {
        DriverStart.driver = new EventFiringDecorator(new LogDriverActions()).decorate(driver);
        PageFactory.initElements(DriverStart.driver, this);
    }

    public WebElement findBrand(String brandTitle) {
        WebElement rightBrand = null;

        for(WebElement brand: filterBrandsList) {
            String brandLink = brand.getAttribute("innerText").strip();
            if(brandLink.equals(brandTitle)) {
                rightBrand = brand;
                break;
            }
        }

        return rightBrand;
    }

    public void printProductsByQuantity(int quantity) {
        for(int i = 0; i < quantity; i++) {
            String title = products.get(i).findElement(By.xpath(".//p[@class='product-tile-name__text']//span[@data-at-product-tile-title]")).getAttribute("innerText").strip();
            String price = products.get(i).findElement(By.xpath(".//span[@class='product-tile-price__text product-tile-price__text--actual']")).getAttribute("innerText").strip();

            logger.info("Название {} товара = {}", i + 1, title);
            logger.info("Цена {} товара = {}", i + 1, price);
        }
    }

    public boolean checkFilterByLetter() {
        for(int i = 0; i < 10; i++) {
            String title = filterBrandsList.get(i).getAttribute("innerText").strip();
            logger.info(title);

            if(!title.startsWith("A"))
                return false;
        }

        return true;
    }

    public boolean checkAvailability() {
        for(WebElement product: products) {
            if (product.getAttribute("innerText").contains("Нет в наличии"))
                return false;
        }
        return true;
    }
}

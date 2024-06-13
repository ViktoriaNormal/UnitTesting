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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LetuPage {

    public String letuURL = "https://www.letu.ru/";

    @FindBy(xpath = "//a[@href='/brands' and @class='header-v4-horizontal-menu__item']")
    public WebElement brandsLink;

    @FindBy(xpath = "//div[@class='brands-letters__item' and contains(text(), 'A')]")
    public WebElement filterBrandsA;

    @FindBy(xpath = "//div[@class='brand-item__link']/a")
    public List<WebElement> filterBrandsList;

    @FindBy(xpath = "//div[@data-at-filter-dimension='sku.stockAvailability']/child::div[1]")
    public WebElement filterAvailability;

    @FindBy(xpath = "//p[contains(text(), 'Есть в наличии')]/parent::*/preceding-sibling::*")
    public WebElement checkboxAvailability;

    @FindBy(xpath = "//a[@class='le-link product-tile__item-container']")
    public List<WebElement> products;

    @FindBy(xpath = "//button[contains(@class, 'header-v4-catalog__button')]")
    public WebElement catalogButton;

    @FindBy(xpath = "//a[contains(text(), 'Уход за кожей')]")
    public WebElement category;

    @FindBy(xpath = "//a[contains(text(), 'Средства для умывания')]")
    public WebElement subcategoryCleanser;

    @FindBy(xpath = "//button[contains(@class, 'products-list-filter-select-v4__options-btn')]")
    public WebElement sortButton;

    @FindBy(xpath = "//span[contains(text(), ' По возрастанию цены ')]/following-sibling::*/input")
    public WebElement sortByIncreasing;

    @FindBy(xpath = "//input[@placeholder='Поиск']")
    public WebElement searchInput;

    @FindBy(xpath = "//button[@title='Поиск']")
    public WebElement searchButton;

    @FindBy(xpath = "//div[@id='filter-menu-9']")
    public WebElement costFilter;

    @FindBy(xpath = "//input[contains(@placeholder, 'от')]")
    public WebElement inputMinPrice;

    @FindBy(xpath = "//input[contains(@placeholder, 'до')]")
    public WebElement inputMaxPrice;

    @FindBy(xpath = "//a[@href='/cart']")
    public WebElement cartButton;

    @FindBy(xpath = "//div[@class='common-commerce-item__product']")
    public List<WebElement> cartProducts;

    @FindBy(xpath = "//div[contains(@class,'le-modal--is-opened')]")
    public List<WebElement> cartModalWindow;

    @FindBy(xpath = "//button[@data-at-sku-add-to-cart]")
    public WebElement modalCartButton;

    @FindBy(xpath = "//button[@class='le-modal__btn-close']")
    public WebElement buttonClose;

    @FindBy(xpath = "//button[@class='le-button cart-commerce-items__controls-remove le-button--theme-white le-button--size-md le-button--with-label']")
    public WebElement deleteButton;

    @FindBy(xpath = "//button[@class='le-button le-button--theme-primary le-button--size-md le-button--with-label le-button--rounded']")
    public WebElement confirmDeleteButton;

    private final Logger logger = LoggerFactory.getLogger(LetuTests.class);

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

    public boolean checkSort() {
        for(int i = 0; i < 10; i++) {
            String curPrice = products.get(i).findElement(By.xpath(".//span[@class='product-tile-price__text product-tile-price__text--actual']")).getAttribute("innerText");
            String nextPrice = products.get(i + 1).findElement(By.xpath(".//span[@class='product-tile-price__text product-tile-price__text--actual']")).getAttribute("innerText");

            logger.info("Цена {} товара из отсортированного по возрастанию списка = {}", i + 1, curPrice);

            if(priceFormat(curPrice) > priceFormat(nextPrice))
                return false;
        }

        return true;
    }

    private int priceFormat(String price) {
        String priceFormat = price.replaceAll("[^0-9]", "");
        return Integer.parseInt(priceFormat);
    }

    public boolean checkRequestProducts() {
        for(int i = 0; i < 10; i++) {
            String description = products.get(i).findElement(By.xpath(".//span[@data-at-product-tile-title]")).getAttribute("innerText").strip().toLowerCase();
            logger.info("Описание {} товара по результату запроса: {}", i + 1, description);

            if(!description.contains("шампунь"))
                return false;
        }

        return true;
    }

    public boolean checkCostFilter() {
        for(int i = 0; i < 10; i++) {
            String price = products.get(i).findElement(By.xpath(".//span[@class='product-tile-price__text product-tile-price__text--actual']")).getAttribute("innerText");
            logger.info("Цена {} товара составляет {}", i + 1, price);

            if(priceFormat(price) < 500 || priceFormat(price) > 1500)
                return false;
        }

        return true;
    }

    public List<WebElement> rememberProducts() {
        List<WebElement> productsList = new ArrayList<>();

        for(int i = 0; i < 4; i++)
           productsList.add(products.get(i));

        return productsList;
    }

    public boolean checkChangeButtonText() {
        for(int i = 0; i < 4; i++) {
            String buttonName = products.get(i).findElement(By.xpath(".//button[contains(@class, 'product-tile-actions__button')]")).getAttribute("innerText");
            logger.info("Название кнопки на карточке {} товара в результате добавления в корзину - \"{}\"", i + 1, buttonName);

            if(!buttonName.contains("В корзине"))
                return false;
        }

        return true;
    }

    public boolean compareProducts(List<WebElement> rememberProducts) {
        if(rememberProducts.size() <= cartProducts.size()) {
            for (int i = 0; i < rememberProducts.size(); i++) {
                String titleAddedProduct = rememberProducts.get(i).findElement(By.xpath(".//p[@class='product-tile-name__text']//span[@data-at-product-tile-title]")).getAttribute("innerText").strip();
                String priceAddedProduct = rememberProducts.get(i).findElement(By.xpath(".//span[@class='product-tile-price__text product-tile-price__text--actual']")).getAttribute("innerText").strip();

                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                String titleCartProduct = cartProducts.get(cartProducts.size() - i - 1).findElement(By.xpath(".//a[contains(@class, 'commerce-item-info__product-name')]")).getAttribute("innerText").strip();
                String priceCartProduct = cartProducts.get(cartProducts.size() - i - 1).findElement(By.xpath(".//span[@class='commerce-item-price__final']")).getAttribute("innerText").strip();

                logger.info("Название {} товара, запомненного ранее, - \"{}\". Название {} товара, добавленного в корзину, - \"{}\".", i + 1, titleAddedProduct, i + 1, titleCartProduct);
                logger.info("Цена {} товара, запомненного ранее, составляет {}. Цена {} товара, добавленного в корзину, составляет {}.", i + 1, priceAddedProduct, i + 1, priceCartProduct);

                if(!titleAddedProduct.equals(titleCartProduct) || priceFormat(priceAddedProduct) != priceFormat(priceCartProduct))
                    return false;
            }
        }
        else
            return false;

        return true;
    }

    public void printCartProducts() {
        for(WebElement cartProduct: cartProducts) {
            String title = cartProduct.findElement(By.xpath(".//a[contains(@class, 'commerce-item-info__product-name')]")).getAttribute("innerText").strip();
            String price = cartProduct.findElement(By.xpath(".//span[@class='commerce-item-price__final']")).getAttribute("innerText").strip();

            logger.info("Название товара = {}", title);
            logger.info("Цена товара = {}", price);
        }
    }
}

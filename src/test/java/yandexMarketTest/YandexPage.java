package yandexMarketTest;

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

public class YandexPage {

    public String yandexURL = "https://market.yandex.ru";

    @FindBy(xpath = "//div[@data-zone-name='catalog']/button")
    public WebElement catalogButton;

    @FindBy(xpath = "//li/a/span[contains(text(), 'Электроника')]")
    public WebElement electronics;

    @FindBy(xpath = "//a[contains(@href, '/catalog--noutbuki/')]")
    public WebElement laptops;

    @FindBy(xpath = "//div[@data-apiary-widget-name='@light/Organic']")
    public List<WebElement> products;

    @FindBy(xpath = "//input[contains(@id, 'range-filter-field-glprice') and contains(@id, 'min')]")
    public WebElement inputMinPrice;

    @FindBy(xpath = "//input[contains(@id, 'range-filter-field-glprice') and contains(@id, 'max')]")
    public WebElement inputMaxPrice;

    private final Logger logger = LoggerFactory.getLogger(YandexTest.class);

    public YandexPage(WebDriver driver) {
        DriverStart.driver = new EventFiringDecorator(new LogDriverActions()).decorate(driver);
        PageFactory.initElements(DriverStart.driver, this);
    }

    public void printTitleAndPrice() {
        for(int i = 0; i < 5; i++) {
            String title = products.get(i).findElement(By.tagName("h3")).getText();
            String price = products.get(i).findElement(By.xpath(".//span[@data-auto='snippet-price-current']/span[1]")).getText();

            logger.info("Название {} товара = {}", i + 1, title);
            logger.info("Цена {} товара = {}", i + 1, price);
        }
    }

    public boolean checkFilterPrice() {
        for(int i = 0; i < 5; i++) {
            String price = products.get(i).findElement(By.xpath(".//span[@data-auto='snippet-price-current']/span[1]")).getText();

            logger.info("Цена {} товара из отфильтрованного списка = {}", i + 1, price);

            String priceFormat = price.replaceAll("[^0-9]", "");
            int intPriceFormat = Integer.parseInt(priceFormat);

            if(intPriceFormat < 60000 || intPriceFormat > 110000)
                return false;
        }

        return true;
    }
}

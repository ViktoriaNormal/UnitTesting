package PageObject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

@ExtendWith(TestListener.class)
public class JavaTest extends DriverStart {
   @Test
    public void Test() {
       _driver.get(_urlGoogle);
       System.out.println(_login);
    }

    @Test
    public void Test1() throws InterruptedException {
       _driver.get(_urlYandex);
       WaitElement("//input[@placeholder='Найдётся всё']");
       _wait.until(visibilityOfElementLocated(By.xpath("//input[@placeholder='Найдётся всё']")));
       _driver.findElement(By.xpath("//input[@placeholder='Найдётся всё']")).sendKeys("Рецепт дрожжевого теста для воздушной пиццы");
       _driver.findElement(By.xpath("//input[@placeholder='Найдётся всё']")).sendKeys(Keys.ENTER);
       _driver.findElement(By.xpath("//li[@class='serp-item serp-item_card '][1]/div/div[1]//h2")).click();
       Thread.sleep(1500);
    }

//    allure serve target/allure-results - генерация allure-отчёта
}

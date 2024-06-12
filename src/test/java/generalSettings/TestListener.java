package generalSettings;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.logging.LogType;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestListener implements TestWatcher {

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        Allure.getLifecycle().addAttachment(
                "Скрин во время падения теста", "image/png", "png",
                ((TakesScreenshot) DriverStart.driver).getScreenshotAs(OutputType.BYTES)
        );

        Allure.addAttachment("Логи в  результате падения теста: ", String.valueOf(DriverStart.driver.manage().logs().get(LogType.BROWSER).getAll()));

        try {
            File failedImage = ((TakesScreenshot) DriverStart.driver).getScreenshotAs(OutputType.FILE);
            DateTimeFormatter format = DateTimeFormatter.ofPattern("uuuu-MMM-dd-HH-mm-ss");
            String imagePath = "target\\failedImages\\failed_test" + context.getDisplayName().replaceAll(" ", "_") + "_completed_" + LocalDateTime.now().format(format) + ".png";
            FileUtils.copyFile(failedImage, new File(imagePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        WebDriverManager.chromedriver().quit();
        DriverStart.driver.quit();
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        Allure.getLifecycle().addAttachment(
                "Скрин в результате успешного прохождения теста", "image/png", "png",
                ((TakesScreenshot) DriverStart.driver).getScreenshotAs(OutputType.BYTES)
        );
        Allure.addAttachment("Логи в результате успешного прохождения теста: ", String.valueOf(DriverStart.driver.manage().logs().get(LogType.BROWSER).getAll()));
        WebDriverManager.chromedriver().quit();
        DriverStart.driver.quit();
    }
}

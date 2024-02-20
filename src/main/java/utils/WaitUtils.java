package utils;

import driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {

    public static WebDriverWait getExplicitWait() {
        return new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10));
    }

    public static WebElement waitUntilPresenceOfElementLocated(By locator) {
        return getExplicitWait().until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static WebElement waitUntilVisibilityOfElementLocated(By locator) {
        return getExplicitWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}
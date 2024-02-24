package utils;

import driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {

    private static WebDriverWait EXPLICIT_WAIT = null;

    public static WebDriverWait getExplicitWait() {
        if (EXPLICIT_WAIT == null) {
            EXPLICIT_WAIT = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10));
            return EXPLICIT_WAIT;
        }
        return EXPLICIT_WAIT;
    }

    public static WebElement waitUntilPresenceOfElementLocated(By locator) {
        return getExplicitWait().until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static WebElement waitUntilVisibilityOfElementLocated(By locator) {
        return getExplicitWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}

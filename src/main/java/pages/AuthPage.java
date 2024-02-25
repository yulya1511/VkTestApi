package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import utils.ResourceConstants;

import java.nio.file.LinkOption;

import static utils.ResourcesUtils.getResource;
import static utils.WaitUtils.waitUntilPresenceOfElementLocated;
import static utils.WaitUtils.waitUntilVisibilityOfElementLocated;

public class AuthPage {

    private By INPUT_LOGIN = By.xpath("//*[contains(@class,'VkIdForm__input')]");
    private By INPUT_PASSWORD = By.xpath("//input[@type='password']");
    private final String LOGIN = "LOGIN";
    private final String PASSWORD = "PASSWORD";

    // mvn clean test -Dlogin=79213645919 -Dpassword=qwer123$
// private final String LOGIN = System.getProperty("login");
// private final String PASSWORD = System.getProperty("password");

    public FeedPage signIn() {
        waitUntilPresenceOfElementLocated(INPUT_LOGIN)
                .sendKeys(getResource(ResourceConstants.TEST_DATA.getResource(), LOGIN), Keys.ENTER);
        waitUntilVisibilityOfElementLocated(INPUT_PASSWORD)
                .sendKeys(getResource(ResourceConstants.TEST_DATA.getResource(), PASSWORD), Keys.ENTER);
        return new FeedPage();
    }
}

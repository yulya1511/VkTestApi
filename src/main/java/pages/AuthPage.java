package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static utils.WaitUtils.waitUntilPresenceOfElementLocated;
import static utils.WaitUtils.waitUntilVisibilityOfElementLocated;

public class AuthPage {

    private By INPUT_LOGIN = By.xpath("//*[contains(@class,'VkIdForm__input')]");
    private final String LOGIN = "79213645919";
    // mvn clean test -Dlogin=79213645919 -Dpassword=qwer123$
//        private final String LOGIN = System.getProperty("login");
    private By INPUT_PASSWORD = By.xpath("//input[@type='password']");
    private final String PASSWORD = "qwer123$";
//    private final String PASSWORD = System.getProperty("password");

    public FeedPage signIn() {
        waitUntilPresenceOfElementLocated(INPUT_LOGIN).sendKeys(LOGIN, Keys.ENTER);
        waitUntilVisibilityOfElementLocated(INPUT_PASSWORD).sendKeys(PASSWORD, Keys.ENTER);
        return new FeedPage();
    }
}

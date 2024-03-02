package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static utils.WaitUtils.waitUntilPresenceOfElementLocated;
import static utils.WaitUtils.waitUntilVisibilityOfElementLocated;

public class AuthPage {

    private By inputLogin = By.xpath("//*[contains(@class,'VkIdForm__input')]");
    private By inputPassword = By.xpath("//input[@type='password']");

    // mvn clean test -Dlogin=79213645919 -Dpassword=qwer123$
// private final String LOGIN = System.getProperty("login"); СПРОСИТЬ КУДА ЗАНОСИТЬ ЛОГИН в случае данного метода
// private final String PASSWORD = System.getProperty("password");

    public FeedPage signIn(String login, String password) {
        waitUntilPresenceOfElementLocated(inputLogin)
                .sendKeys(login, Keys.ENTER);
        waitUntilVisibilityOfElementLocated(inputPassword)
                .sendKeys(password, Keys.ENTER);
        return new FeedPage();
    }
}

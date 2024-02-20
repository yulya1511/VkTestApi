package pages;

import org.openqa.selenium.By;

import static utils.WaitUtils.waitUntilPresenceOfElementLocated;

public class ProfilePage {

    private By TEXT_FROM_POST = By.xpath("//div[contains(text(), 'My')]");

    public String getTextFromPost() {
        return waitUntilPresenceOfElementLocated(TEXT_FROM_POST).getText();
    }
}


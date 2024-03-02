package pages;

import org.openqa.selenium.By;

import static utils.WaitUtils.waitUntilPresenceOfElementLocated;

public class FeedPage {

    private By goToProfile = By.xpath("//span[text()='My profile']");

    public ProfilePage goingToProfile() {
        waitUntilPresenceOfElementLocated(goToProfile).click();
        return new ProfilePage();
    }
}

package pages;

import org.openqa.selenium.By;

import static utils.WaitUtils.waitUntilPresenceOfElementLocated;

public class FeedPage {

    public ProfilePage goingToProfile() {
        waitUntilPresenceOfElementLocated(By.xpath("//span[text()='My profile']")).click();
        return new ProfilePage();
    }
}

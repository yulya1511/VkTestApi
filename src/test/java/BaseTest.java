import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.WaitUtils;

import static driver.DriverManager.closeDriver;
import static utils.DriverUtils.open;
import static utils.DriverUtils.openWindowMax;
import static utils.ResourcesUtils.getResource;

public class BaseTest {

    protected WebDriverWait wait;
    protected final static String HOST = "URL_HOST";

    @BeforeMethod
    public void setUp() {
        open(getResource(HOST));
        openWindowMax();
        wait = WaitUtils.getExplicitWait();
    }

    @AfterMethod
    public void tearDown() {
        closeDriver();
    }
}
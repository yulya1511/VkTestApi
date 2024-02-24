import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ResourceConstants;

import static driver.DriverManager.closeDriver;
import static utils.DriverUtils.open;
import static utils.DriverUtils.openWindowMax;
import static utils.ResourcesUtils.getResource;

public class BaseTest {

    protected final static String HOST = "URL_HOST";

    @BeforeMethod
    public void setUp() {
        open(getResource(ResourceConstants.CONFIG.getResource(), HOST));
        openWindowMax();
    }

    @AfterMethod
    public void tearDown() {
        closeDriver();
    }
}
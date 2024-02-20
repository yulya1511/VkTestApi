package utils;

import static driver.DriverManager.getDriver;

public class DriverUtils {

    public static void open(String url) {
        getDriver().get(url);
    }

    public static void openWindowMax() {
        getDriver().manage().window().maximize();
    }
}
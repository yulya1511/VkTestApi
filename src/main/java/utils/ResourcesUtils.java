package utils;

import java.util.ResourceBundle;

public class ResourcesUtils {
    public static String getResource (String property, String resourceName){
        return ResourceBundle.getBundle(property).getString(resourceName);
    }
}

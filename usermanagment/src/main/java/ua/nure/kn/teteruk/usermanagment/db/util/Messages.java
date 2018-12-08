package ua.nure.kn.teteruk.usermanagment.db.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("message");

    private Messages() {
    }

    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return "!" + key + "!";
        }
    }
}

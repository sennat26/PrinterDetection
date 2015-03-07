package utils;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * This utility class will take care of the label constants to be<br>
 * displayed in the Agent UI.
 * <br>
 * Copyright (c) 2008, HCL Technologies.<br>
 * May 21, 2009 1:13:41 PM<br>
 * 
 * @author rejithes
 * @version $Id$
 */
public final class I18NHelper {
    
    /** Label constant file path. */
    private static final String BUNDLE_NAME = "I18Nconstants.Constants";

    /** Resource bundle. */
    private static ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(BUNDLE_NAME, Locale.getDefault());

    /**
     * Default constructor.
     * */
    private I18NHelper() {
    }
    
    public static void reloadResourceBundle() {
        RESOURCE_BUNDLE = ResourceBundle
        .getBundle(BUNDLE_NAME, Locale.getDefault());
    }
    
    /**
     * To get the string value for the constant.
     * @param key - constant key
     * @return String - string value for the constant
     * */
    public static String getString(final String key) {

        try {
            return RESOURCE_BUNDLE.getString(key);
            
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}

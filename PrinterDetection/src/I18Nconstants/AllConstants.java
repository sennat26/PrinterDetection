package I18Nconstants;

import utils.I18NHelper;

/**
 * Constants for UI.
 * @author senthilnathan_c
 */
public final class AllConstants {

    /**empty private constructor. */
    private AllConstants() {
    }
    
    // Table Header Constants
    /** SL_NUMBER.*/
    public static final String SL_NUMBER = AllConstants.getString("SL_NUMBER");
    /** MANUFACTURER.*/
    public static final String MANUFACTURER = AllConstants.getString("MANUFACTURER");
    /** MODEL .*/
    public static final String MODEL = AllConstants.getString("MODEL");
    /** IPADDRESS .*/
    public static final String IPADDRESS = AllConstants.getString("IPADDRESS");
    /** NAME.*/
    public static final String NAME = AllConstants.getString("NAME");
    /** TYPE.*/
    public static final String TYPE = AllConstants.getString("TYPE");
    /** PART_NO.*/
    public static final String PART_NO = AllConstants.getString("PART_NO");
    /** UNITS.*/
    public static final String UNITS = AllConstants.getString("UNITS");
    /** MAX_CAPACITY.*/
    public static final String MAX_CAPACITY = AllConstants.getString("MAX_CAPACITY");
    /** CURRENT_LEVEL.*/
    public static final String CURRENT_LEVEL = AllConstants.getString("CURRENT_LEVEL");
    /** PERCENTAGE.*/
    public static final String PERCENTAGE = AllConstants.getString("PERCENTAGE");
    /** CLASS.*/
    public static final String CLASS = AllConstants.getString("CLASS");
    
   
    //Button Constants
    /** GET_CONSUMABLES.*/
    public static final String GET_CONSUMABLES = AllConstants.getString("GET_CONSUMABLES");
    /** EXIT.*/
    public static final String EXIT = AllConstants.getString("EXIT");
    
    
    //Label Constants
    /** ENTER_IP.*/
    public static final String ENTER_IP = AllConstants.getString("ENTER_IP");
    /** DEVICE_FOUND.*/
    public static final String DEVICE_FOUND = AllConstants.getString("DEVICE_FOUND");
    /** SEARCHING.*/
    public static final String SEARCHING = AllConstants.getString("SEARCHING");
    /** DISCOVERED_PRINTERS.*/
    public static final String DISCOVERED_PRINTERS = AllConstants.getString("DISCOVERED_PRINTERS");
    /** DISCOVERED_PRINTERS.*/
    public static final String DISCOVERED_SUBNETS = AllConstants.getString("DISCOVERED_SUBNETS");
    /** NO_DEV_FOUND.*/
    public static final String NO_DEV_FOUND = AllConstants.getString("NO_DEV_FOUND");
    /** CONSUMABLES_FOUND.*/
    public static final String CONSUMABLES_FOUND = AllConstants.getString("CONSUMABLES_FOUND");
    /** CONSUMABLES_EMPTY*/
    public static final String CONSUMABLES_EMPTY = AllConstants.getString("CONSUMABLES_EMPTY");
    
    
    //message Constants
    /** ENTER_VALID_IPS.*/
    public static final String ENTER_VALID_IP = AllConstants.getString("ENTER_VALID_IP");
    
    /**
     * Get the string value for the constant.
     * @param key - KEY string
     * @return String - Value of constant
     * */
    private static String getString(final String key) {
        return I18NHelper.getString(key);
    }
}
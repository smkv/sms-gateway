package ee.smkv.sms.senders.at;


import java.util.LinkedHashMap;
import java.util.Map;

public class CMEError extends RuntimeException {

    public static final Map<String, String> ERRORS = new LinkedHashMap<String, String>() {
        {
            put("+CME ERROR: 0", "Phone failure");
            put("+CME ERROR: 1", "No connection to phone");
            put("+CME ERROR: 2", "Phone adapter link reserved");
            put("+CME ERROR: 3", "Operation not allowed");
            put("+CME ERROR: 4", "Operation not supported");
            put("+CME ERROR: 5", "PH_SIM PIN required");
            put("+CME ERROR: 6", "PH_FSIM PIN required");
            put("+CME ERROR: 7", "PH_FSIM PUK required");
            put("+CME ERROR: 10", "SIM not inserted");
            put("+CME ERROR: 11", "SIM PIN required");
            put("+CME ERROR: 12", "SIM PUK required");
            put("+CME ERROR: 13", "SIM failure");
            put("+CME ERROR: 14", "SIM busy");
            put("+CME ERROR: 15", "SIM wrong");
            put("+CME ERROR: 16", "Incorrect password");
            put("+CME ERROR: 17", "SIM PIN2 required");
            put("+CME ERROR: 18", "SIM PUK2 required");
            put("+CME ERROR: 20", "Memory full");
            put("+CME ERROR: 21", "Invalid index");
            put("+CME ERROR: 22", "Not found");
            put("+CME ERROR: 23", "Memory failure");
            put("+CME ERROR: 24", "Text string too long");
            put("+CME ERROR: 25", "Invalid characters in text string");
            put("+CME ERROR: 26", "Dial string too long");
            put("+CME ERROR: 27", "Invalid characters in dial string");
            put("+CME ERROR: 30", "No network service");
            put("+CME ERROR: 31", "Network timeout");
            put("+CME ERROR: 32", "Network not allowed, emergency calls only");
            put("+CME ERROR: 40", "Network personalization PIN required");
            put("+CME ERROR: 41", "Network personalization PUK required");
            put("+CME ERROR: 42", "Network subset personalization PIN required");
            put("+CME ERROR: 43", "Network subset personalization PUK required");
            put("+CME ERROR: 44", "Service provider personalization PIN required");
            put("+CME ERROR: 45", "Service provider personalization PUK required");
            put("+CME ERROR: 46", "Corporate personalization PIN required");
            put("+CME ERROR: 47", "Corporate personalization PUK required");
            put("+CME ERROR: 48", "PH-SIM PUK required");
            put("+CME ERROR: 100", "Unknown error");
            put("+CME ERROR: 103", "Illegal MS");
            put("+CME ERROR: 106", "Illegal ME");
            put("+CME ERROR: 107", "GPRS services not allowed");
            put("+CME ERROR: 111", "PLMN not allowed");
            put("+CME ERROR: 112", "Location area not allowed");
            put("+CME ERROR: 113", "Roaming not allowed in this location area");
            put("+CME ERROR: 126", "Operation temporary not allowed");
            put("+CME ERROR: 132", "Service operation not supported");
            put("+CME ERROR: 133", "Requested service option not subscribed");
            put("+CME ERROR: 134", "Service option temporary out of order");
            put("+CME ERROR: 148", "Unspecified GPRS error");
            put("+CME ERROR: 149", "PDP authentication failure");
            put("+CME ERROR: 150", "Invalid mobile class");
            put("+CME ERROR: 256", "Operation temporarily not allowed");
            put("+CME ERROR: 257", "Call barred");
            put("+CME ERROR: 258", "Phone is busy");
            put("+CME ERROR: 259", "User abort");
            put("+CME ERROR: 260", "Invalid dial string");
            put("+CME ERROR: 261", "SS not executed");
            put("+CME ERROR: 262", "SIM Blocked");
            put("+CME ERROR: 263", "Invalid block");
            put("+CME ERROR: 772", "SIM powered down");

        }
    };
    
    public final String code;
    public final String description;

    private CMEError(String code, String description) {
        super(String.format("%s (%s)" , code , description));
        this.code = code;
        this.description = description;
    }
    
    public static boolean isCMEError(String line){
        return line.startsWith("+CMS ERROR: ");
    }
    
    public static CMEError parse(String line){
        String code = line.split(": ")[1];
        return new CMEError(code , ERRORS.get(line));
        
    }
    
    public boolean isPinRequired(){
        return "11".equals(code);
    }
    
}

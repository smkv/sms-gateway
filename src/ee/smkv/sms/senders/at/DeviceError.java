package ee.smkv.sms.senders.at;


import java.util.LinkedHashMap;
import java.util.Map;

public class DeviceError extends RuntimeException {

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

            put("+CMS ERROR: 300", "ME failure");
            
            put("+CMS ERROR: 1", "Unassigned (unallocated) number");
            put("+CMS ERROR: 8", "Operator determined barring");
            put("+CMS ERROR: 10", "Call barred");
            put("+CMS ERROR: 17", "Network failure");
            put("+CMS ERROR: 21", "Short message transfer rejected");
            put("+CMS ERROR: 22", "Congestion or Memory capacity exceeded");
            put("+CMS ERROR: 27", "Destination out of service");
            put("+CMS ERROR: 28", "Unidentified subscriber");
            put("+CMS ERROR: 29", "Facility rejected");
            put("+CMS ERROR: 30", "Unknown subscriber");
            put("+CMS ERROR: 38", "Network out of order");
            put("+CMS ERROR: 41", "Temporary failure");
            put("+CMS ERROR: 42", "Congestion");
            put("+CMS ERROR: 47", "Resources unavailable, unspecified");
            put("+CMS ERROR: 50", "Requested facility not subscribed");
            put("+CMS ERROR: 69", "Requested facility not implemented");
            put("+CMS ERROR: 81", "Invalid short message transfer reference value");
            put("+CMS ERROR: 95", "Invalid message, unspecified");
            put("+CMS ERROR: 96", "Invalid mandatory information");
            put("+CMS ERROR: 97", "Message type non-existent or not implemented");
            put("+CMS ERROR: 98", "Message not compatible with short message protocol state");
            put("+CMS ERROR: 99", "Information element non-existent or not implemented");
            put("+CMS ERROR: 111", "Protocol error, unspecified");
            put("+CMS ERROR: 127", "Interworking, unspecified");
            put("+CMS ERROR: 128", "Telematic interworking not supported");
            put("+CMS ERROR: 129", "Short message Type 0 not supported");
            put("+CMS ERROR: 130", "Cannot replace short message");
            put("+CMS ERROR: 143", "Unspecified TP-PID error");
            put("+CMS ERROR: 144", "Data coding scheme (alphabet) not supported");
            put("+CMS ERROR: 145", "Message class not supported");

             put("+CMS ERROR: 159", "Unspecified TP-DCS error x x");
             put("+CMS ERROR: 160", "Command cannot be actioned x");
             put("+CMS ERROR: 161", "Command unsupported x");
             put("+CMS ERROR: 175", "Unspecified TP-Command error x");
             put("+CMS ERROR: 176", "TPDU not supported x x");
             put("+CMS ERROR: 192", "SC busy x");
             put("+CMS ERROR: 193", "No SC subscription x");
             put("+CMS ERROR: 194", "SC system failure x");
             put("+CMS ERROR: 195", "Invalid SME address x");
             put("+CMS ERROR: 196", "Destination SME barred x");
             put("+CMS ERROR: 197", "SM Rejected-Duplicate SM x");
             put("+CMS ERROR: 198", "TP-VPF not supported X");
             put("+CMS ERROR: 199", "TP-VP not supported X");
             put("+CMS ERROR: 208", "SIM SMS storage full x");
             put("+CMS ERROR: 209", "No SMS storage capability in SIM x");
             put("+CMS ERROR: 210", "Error in MS x");
             put("+CMS ERROR: 211", "Memory Capacity Exceeded X");
             put("+CMS ERROR: 212", "SIM Application Toolkit Busy x x");
             put("+CMS ERROR: 255", "Unspecified error cause");


            put("+CMS ERROR: 301", "SMS service of ME reserved");
            put("+CMS ERROR: 302", "Operation not allowed");
            put("+CMS ERROR: 303", "Operation not supported");
            put("+CMS ERROR: 304", "Invalid PDU mode parameter");
            put("+CMS ERROR: 305", "Invalid text mode parameter");
            put("+CMS ERROR: 310", "SIM not inserted");
            put("+CMS ERROR: 311", "SIM PIN required");
            put("+CMS ERROR: 312", "PH-SIM PIN required");
            put("+CMS ERROR: 313", "SIM failure");
            put("+CMS ERROR: 314", "SIM busy");
            put("+CMS ERROR: 315", "SIM wrong");
            put("+CMS ERROR: 316", "SIM PUK required");
            put("+CMS ERROR: 317", "SIM PIN2 required");
            put("+CMS ERROR: 318", "SIM PUK2 required ");
            put("+CMS ERROR: 320", "Memory failure");
            put("+CMS ERROR: 321", "Invalid memory index");
            put("+CMS ERROR: 322", "Memory full");
            put("+CMS ERROR: 330", "SMSC address unknown ");
            put("+CMS ERROR: 331", "No network service");
            put("+CMS ERROR: 332", "Network timeout");
            put("+CMS ERROR: 340", "No +CNMA acknowledgement expected");
            put("+CMS ERROR: 500", "Unknown error");

        }
    };
    
    public final String code;
    public final String description;

    private DeviceError(String code, String description) {
        super(String.format("%s (%s)" , code , description));
        this.code = code;
        this.description = description;
    }
    
    public static boolean isDeviceError(String line){
        return line.startsWith("+CMS ERROR: ") || line.startsWith("+CME ERROR: ");
    }
    
    public static DeviceError parse(String line){
        String code = line.split(": ")[1];
        return new DeviceError(code , ERRORS.get(line));
        
    }
    
    public boolean isPinRequired(){
        return "11".equals(code);
    }
    
}

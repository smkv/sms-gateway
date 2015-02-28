package ee.smkv.sms.senders.at;

public class ATCommand {
    protected String command;

    ATCommand(String command) {
        this.command = command;
    }

    public static ATCommand create(String command){
        return new ATCommand(command);
    }

    protected void handleResponse(String response) {
        boolean valid = response.endsWith("\r\nOK\r\n");
        if(!valid){
            throw new RuntimeException(String.format("Execution of command '%s' failed: %s", command , response ));
        }
    }
    
    protected byte[] getBytes(){
        return (command + '\r').getBytes();
        
    }
    
}

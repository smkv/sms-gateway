package ee.smkv.sms.senders.at;

import ee.smkv.sms.model.SmsMessage;


public class MessageRecipientATCommand extends ATCommand {

    public static ATCommand create(SmsMessage smsMessage) {
        return new MessageRecipientATCommand( String.format("AT+CMGS=\"%s\"", smsMessage.getRecipient()));
    }

    MessageRecipientATCommand(String command) {
        super(command);
    }

    @Override
    protected void handleResponse(String response) {
        boolean valid = ">".equals(response.trim());
        if(!valid){
            throw new RuntimeException("Not valid answer from AT device: " + response);
            
        }
    }
}

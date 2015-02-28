package ee.smkv.sms.senders.at;

import ee.smkv.sms.model.SmsMessage;


public class MessageTextATCommand extends ATCommand {

    public static ATCommand create(SmsMessage smsMessage) {
        return new MessageTextATCommand(smsMessage.getText() );
    }

    MessageTextATCommand(String command) {
        super(command);
    }


    @Override
    protected byte[] getBytes() {
        return (command + '\u001A').getBytes();
    }
}

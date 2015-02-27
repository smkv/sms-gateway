package ee.smkv.sms.senders;

import ee.smkv.sms.model.SmsMessage;

public interface SmsSender {
    void send(SmsMessage smsMessage);
}

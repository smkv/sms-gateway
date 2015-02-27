package ee.smkv.sms;

import ee.smkv.sms.model.SmsMessage;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class SmsServiceTest {
    
    SmsService smsService ;

    @Before
    public void setUp() throws Exception {
        smsService = new SmsService();
        smsService.smsWhiteList = new ArrayList<>();
        smsService.smsWhiteList.add("+372500000");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateRecipientNull() throws Exception {
        smsService.validateRecipient(new SmsMessage(null,""));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testValidateRecipientEmpty() throws Exception {
        smsService.validateRecipient(new SmsMessage("",""));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testValidateRecipientNotInWhiteList() throws Exception {
        smsService.validateRecipient(new SmsMessage("+372500001",""));
    }
    @Test
    public void testValidateRecipientInWhiteList() throws Exception {
        smsService.validateRecipient(new SmsMessage("+372500000",""));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateMessageNull() throws Exception {
        smsService.validateMessage(new SmsMessage("", null));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testValidateMessageEmpty() throws Exception {
        smsService.validateMessage(new SmsMessage("", ""));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testValidateMessageBig() throws Exception {
        smsService.validateMessage(new SmsMessage("", StringUtils.repeat('*', 161)));
    }
    @Test
    public void testValidateMessageNormal() throws Exception {
        smsService.validateMessage(new SmsMessage("", StringUtils.repeat('*', 160)));
    }
}
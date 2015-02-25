package ee.smkv.sms.resources;

import ee.smkv.sms.SmsMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/sms")
public class SmsGateway {

    @RequestMapping(method = RequestMethod.GET)
    public List<SmsMessage> get(){
        return Arrays.asList();
    }

    @RequestMapping(method = RequestMethod.POST)
    public boolean post(SmsMessage smsMessage){
        System.out.println(smsMessage);
        return false;
    }

}

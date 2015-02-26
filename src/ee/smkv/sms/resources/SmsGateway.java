package ee.smkv.sms.resources;

import ee.smkv.sms.SmsService;
import ee.smkv.sms.model.SmsMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/sms")
public class SmsGateway {

    @Autowired
    SmsService smsService;

    @RequestMapping(method = RequestMethod.POST)
    public void post(@RequestBody SmsMessage smsMessage) {
        smsService.send(smsMessage);
    }

}

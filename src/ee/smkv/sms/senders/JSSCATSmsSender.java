package ee.smkv.sms.senders;

import ee.smkv.sms.model.SmsMessage;
import ee.smkv.sms.senders.at.ATCommand;
import ee.smkv.sms.senders.at.ATDevice;
import ee.smkv.sms.senders.at.MessageRecipientATCommand;
import ee.smkv.sms.senders.at.MessageTextATCommand;
import jssc.SerialPortException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component("at")
//@Lazy
public class JSSCATSmsSender implements SmsSender {

    private final static Logger LOG = Logger.getLogger(JSSCATSmsSender.class);

    @Autowired
    Environment environment;

    ATDevice device;

    @PostConstruct
    public void init() throws Exception {
        device = new ATDevice(environment.getProperty("sms.at.port"));
        device.open();
        checkATSupport();
        selectTextFormat();
    }

    private void selectTextFormat() {
        try {
            device.execute(ATCommand.create("AT+CMGF=1"));// set TEXT format
        } catch (Exception e) {
            LOG.error(e.getMessage() ,e);
        }
    }

    private void checkATSupport() {
        try {
            device.execute(ATCommand.create("AT")); // check AT support
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @PreDestroy
    public void destroy() throws Exception {
        device.close();
    }


    @Override
    public void send(SmsMessage smsMessage) {
        try {
            device.execute(MessageRecipientATCommand.create(smsMessage));
            device.execute(MessageTextATCommand.create(smsMessage));

        } catch (SerialPortException e) {
            LOG.error(e.getMessage(), e);
        }

    }

}

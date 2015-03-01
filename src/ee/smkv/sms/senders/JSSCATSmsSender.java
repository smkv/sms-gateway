package ee.smkv.sms.senders;

import ee.smkv.sms.model.SmsMessage;
import ee.smkv.sms.senders.at.ATCommand;
import ee.smkv.sms.senders.at.ATDevice;
import jssc.SerialPortException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component("at")
@Lazy
public class JSSCATSmsSender implements SmsSender {

    private final static Logger LOG = Logger.getLogger(JSSCATSmsSender.class);

    @Autowired
    Environment environment;

    ATDevice device;

    @PostConstruct
    public void init() throws Exception {
        device = new ATDevice(environment.getProperty("sms.at.port") , environment.getProperty("sms.at.pin"));
        device.open();

        logDeviceInformation();
        selectTextFormat();
    }


    private void selectTextFormat() throws SerialPortException {
        device.execute(new ATCommand("AT+CMGF=1"));

    }

    private void logDeviceInformation() throws SerialPortException {
        LOG.info("Device Information: \n" + device.execute(new ATCommand("ATI")));
    }


    @PreDestroy
    public void destroy() throws Exception {
        device.close();
    }


    @Override
    public void send(SmsMessage smsMessage) {
        try {
            device.execute(new ATCommand(String.format("AT+CMGS=\"%s\"", smsMessage.getRecipient()), smsMessage.getText()));
        } catch (SerialPortException e) {
            LOG.error(e.getMessage(), e);
        }

    }

}

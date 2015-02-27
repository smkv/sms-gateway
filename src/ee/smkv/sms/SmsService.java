package ee.smkv.sms;


import ee.smkv.sms.model.SmsMessage;
import ee.smkv.sms.senders.SmsSenderFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class SmsService {

    private final static Logger LOG = Logger.getLogger(SmsService.class);

    private LinkedBlockingQueue<SmsMessage> queue = new LinkedBlockingQueue<SmsMessage>();
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Value("#{'${sms.whiteList}'.split('\\s*,\\s*')}")
    protected List<String> smsWhiteList = Collections.emptyList();

    @Value("#{'${sms.blackList}'.split('\\s*,\\s*')}")
    protected List<String> smsBlackList = Collections.emptyList();

    @Autowired
    protected SmsSenderFactory smsSenderFactory;


    @PostConstruct
    void init() {
        LOG.info("Initialization of SMS Service...");
        LOG.info("SMS White list: " + smsWhiteList);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!Thread.interrupted()) {
                        SmsMessage smsMessage = queue.take();
                        smsSenderFactory.getSmsSender().send(smsMessage);
                    }
                } catch (InterruptedException ignore) {
                }
            }
        });

        LOG.info(String.format("SMS Service initialized"));
    }

    public void send(SmsMessage smsMessage) {
        validate(smsMessage);
        queue.offer(smsMessage);
    }

    protected void validate(SmsMessage smsMessage) {
        validateRecipient(smsMessage);
        validateMessage(smsMessage);
    }

    protected void validateRecipient(SmsMessage smsMessage) {
        if (StringUtils.isEmpty(smsMessage.getRecipient())) {
            throw new IllegalArgumentException(String.format("The recipient of SMS is empty"));
        }
        if (smsBlackList.contains(smsMessage.getRecipient())) {
            throw new IllegalArgumentException(String.format("The recipient '%s' is in black recipients list", smsMessage.getRecipient()));
        }
        if (hasWhiteList() && !smsWhiteList.contains(smsMessage.getRecipient())) {
            throw new IllegalArgumentException(String.format("The recipient '%s' is not in white recipients list", smsMessage.getRecipient()));
        }
    }

    private boolean hasWhiteList() {
        return !smsWhiteList.isEmpty() && (smsWhiteList.size() > 1 || !StringUtils.isEmpty(smsWhiteList.get(0)));
    }

    protected void validateMessage(SmsMessage smsMessage) {
        if (StringUtils.isEmpty(smsMessage.getText())) {
            throw new IllegalArgumentException(String.format("The message of SMS is empty"));
        }
        if (smsMessage.getText().length() > 160) {
            throw new IllegalArgumentException(String.format("The message length of SMS is more than 160 symbols"));
        }
    }


    @PreDestroy
    void destroy() {
        LOG.info("Destroying of SMS Service...");
        executorService.shutdownNow();
        LOG.info("SMS Service destroyed");
    }
}

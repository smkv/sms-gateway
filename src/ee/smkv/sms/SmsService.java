package ee.smkv.sms;


import ee.smkv.sms.model.SmsMessage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class SmsService {

    private final static Logger LOG = Logger.getLogger(SmsService.class);

    private LinkedBlockingQueue<SmsMessage> queue = new LinkedBlockingQueue<SmsMessage>();
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Value("#{'${sms.whitelist}'.split('\\s*,\\s*')}")
    private Set<String> smsWhiteList = Collections.emptySet();


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
                        sendSmsUsingGammu(smsMessage);
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

    private void validate(SmsMessage smsMessage) {
        validateRecipient(smsMessage);
        validateMessage(smsMessage);
    }

    private void validateRecipient(SmsMessage smsMessage) {
        if (StringUtils.isEmpty(smsMessage.getRecipient())) {
            throw new IllegalArgumentException(String.format("The recipient of SMS is empty"));
        }
        if (!smsWhiteList.contains(smsMessage.getRecipient())) {
            throw new IllegalArgumentException(String.format("The recipient '%s' is not in allowed recipients list", smsMessage.getRecipient()));
        }
    }

    private void validateMessage(SmsMessage smsMessage) {
        if (StringUtils.isEmpty(smsMessage.getText())) {
            throw new IllegalArgumentException(String.format("The message of SMS is empty"));
        }
        if (smsMessage.getText().length() > 160) {
            throw new IllegalArgumentException(String.format("The message length of SMS is more than 160 symbols"));
        }
    }

    private void sendSmsUsingGammu(SmsMessage smsMessage) {
        System.out.println("Sending message: " + smsMessage);
        try {

            String command = String.format("gammu sendsms TEXT %s -text \"%s\"", smsMessage.getRecipient(), smsMessage.getText().replaceAll("\"", "\\\""));
            LOG.info(command);
            Process process = Runtime.getRuntime().exec(command);
            int exitCode = process.waitFor();
            String output = StreamUtils.copyToString(process.getInputStream(), Charset.defaultCharset());
            LOG.info(output);
            if (exitCode != 0) {
                LOG.error("Execution exit code is: " + exitCode);
            }

        } catch (Exception e) {
            LOG.error("Failed execute command: " + e.getMessage(), e);
        }
    }


    @PreDestroy
    void destroy() {
        LOG.info("Destroying of SMS Service...");
        executorService.shutdownNow();
        LOG.info("SMS Service destroyed");
    }
}

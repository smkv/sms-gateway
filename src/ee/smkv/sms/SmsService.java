package ee.smkv.sms;


import ee.smkv.sms.model.SmsMessage;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class SmsService {

    private LinkedBlockingQueue<SmsMessage> queue = new LinkedBlockingQueue<SmsMessage>();
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private Set<String> allowedRecipients = new LinkedHashSet<String>(Arrays.asList(
      "+3725432101"
    ));


    @PostConstruct
    void init(){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!Thread.interrupted()){
                        SmsMessage smsMessage = queue.take();
                        sendSmsMessageFromQueue(smsMessage);
                    }
                }
                catch (InterruptedException ignore) {
                }
            }
        });
    }

    public void send(SmsMessage smsMessage){
        validate(smsMessage);
        queue.offer(smsMessage);
    }

    private void validate(SmsMessage smsMessage) {
        validateRecipient(smsMessage);
        validateMessage(smsMessage);
    }

    private void validateRecipient(SmsMessage smsMessage) {
        if(StringUtils.isEmpty(smsMessage.getRecipient())){
            throw new IllegalArgumentException(String.format("The recipient of SMS is empty" ));
        }
        if(!allowedRecipients.contains(smsMessage.getRecipient())){
            throw new IllegalArgumentException(String.format("The recipient '%s' is not in allowed recipients list" , smsMessage.getRecipient()));
        }
    }

    private void validateMessage(SmsMessage smsMessage) {
        if(StringUtils.isEmpty(smsMessage.getText())){
            throw new IllegalArgumentException(String.format("The message of SMS is empty" ));
        }
        if(smsMessage.getText().length() > 160){
            throw new IllegalArgumentException(String.format("The message length of SMS is more than 160 symbols" ));
        }
    }

    private void sendSmsMessageFromQueue(SmsMessage smsMessage) {
        System.out.println("Sending message: " + smsMessage);
        try {

            Process process = Runtime.getRuntime().exec(String.format("gammu sendsms TEXT %s -text \"%s\"", smsMessage.getRecipient(), smsMessage.getText().replaceAll("\"", "\\\"")));
            int exitCode = process.waitFor();

            BufferedReader reader =
              new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line = "";
            while ((line = reader.readLine())!= null) {
                System.out.println(line);
            }
            System.out.println("ExitCode:" + exitCode);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    @PreDestroy
    void destroy(){
        executorService.shutdownNow();
    }
}

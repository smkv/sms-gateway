package ee.smkv.sms.senders;

import ee.smkv.sms.model.SmsMessage;
import ee.smkv.sms.utils.Maps;
import org.apache.commons.exec.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;

@Component("gammu")
public class GammuSmsSender implements SmsSender {

    private final static Logger LOG = Logger.getLogger(GammuSmsSender.class);

    @Override
    public void send(SmsMessage smsMessage) {
        try {


            CommandLine command = CommandLine.parse("gammu sendsms TEXT ${phone_number} -text ${message_text}",
                    Maps.<String, String>builder()
                            .put("phone_number", smsMessage.getRecipient())
                            .put("message_text", smsMessage.getText())
                            .build()
            );
            LOG.info(command);

            final ByteArrayOutputStream output = new ByteArrayOutputStream();
            DefaultExecutor executor = new DefaultExecutor();
            executor.setStreamHandler(new PumpStreamHandler(output));
            executor.execute(command, new ExecuteResultHandler() {
                @Override
                public void onProcessComplete(int exitValue) {
                    LOG.info(output.toString());
                    if (exitValue != 0) {
                        LOG.error("Execution exit code is: " + exitValue);
                    } else {
                        LOG.info("SMS sent");

                    }
                }

                @Override
                public void onProcessFailed(ExecuteException e) {
                    LOG.error("Failed execute command: " + e.getMessage(), e);
                }
            });


        } catch (Exception e) {
            LOG.error("Failed execute command: " + e.getMessage(), e);
        }
    }
}

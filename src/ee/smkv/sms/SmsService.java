package ee.smkv.sms;

import ee.smkv.sms.model.SmsMessage;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class SmsService {

    private LinkedBlockingQueue<SmsMessage> queue = new LinkedBlockingQueue<>();
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

}

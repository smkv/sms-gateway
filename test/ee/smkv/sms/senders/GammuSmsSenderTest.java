package ee.smkv.sms.senders;

import ee.smkv.sms.model.SmsMessage;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GammuSmsSenderTest {

    @Test
    public void testMakeCommand() throws Exception {
        GammuSmsSender sender = new GammuSmsSender();
        assertEquals("[gammu, sendsms, TEXT, +3725432101, -text, \"Hello World\"]" , sender.makeCommand(new SmsMessage("+3725432101" , "Hello World")).toString());
    }
    @Test
    @Ignore //  Apache CommandLine removes last double quote
    public void testMakeCommandWithQuot() throws Exception {
        GammuSmsSender sender = new GammuSmsSender();
        assertEquals("[gammu, sendsms, TEXT, +3725432101, -text, \"Hello World\\\"Hello World\\\"\"]" , sender.makeCommand(new SmsMessage("+3725432101" , "Hello World \"Hello World\"")).toString());
    }
}
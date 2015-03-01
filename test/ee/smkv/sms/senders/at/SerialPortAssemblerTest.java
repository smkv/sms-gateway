package ee.smkv.sms.senders.at;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SerialPortAssemblerTest {

    @Test
    public void testReadStringLine() throws Exception {
        SerialPortAssembler assembler = new SerialPortAssembler(null);
        assembler.writeToBufferAndNotifyAll("\r\nOK\r\n");
        assertEquals("" , assembler.readStringLine());
        assertEquals("OK" , assembler.readStringLine());

    }  
    @Test
    public void testReadStringLinePrompt() throws Exception {
        SerialPortAssembler assembler = new SerialPortAssembler(null);
        assembler.writeToBufferAndNotifyAll("\r\n> ");
        assertEquals("", assembler.readStringLine());
        assertEquals("> " , assembler.readStringLine());

    }
}
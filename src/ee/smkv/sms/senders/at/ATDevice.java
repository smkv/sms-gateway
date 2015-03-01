package ee.smkv.sms.senders.at;

import jssc.SerialPort;
import jssc.SerialPortException;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

public class ATDevice {
    private final static Logger LOG = Logger.getLogger(ATDevice.class);
    public static final List<String> FINAL_RESULT_CODE = Arrays.asList("OK", "NO CARRIER", "NO DIALTONE", "NO ANSWER", "BUSY", "ERROR");

    private final SerialPort serialPort;
    private SerialPortAssembler assembler;

    public ATDevice(String serialPort) {
        this.serialPort = new SerialPort(serialPort);
        this.assembler = new SerialPortAssembler(this.serialPort);
    }

    public void open() throws SerialPortException {
        if (!serialPort.isOpened()) {
            serialPort.openPort();
            serialPort.setParams(SerialPort.BAUDRATE_115200,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            serialPort.addEventListener(assembler);
        }
    }

    public String execute(ATCommand command) throws SerialPortException {
        StringBuffer responseBuffer = new StringBuffer();
        LOG.info("Sending  command: " + command.getCommand());
        serialPort.writeString(command.getCommand());
        serialPort.writeString("\r");

        String line;
        do {
            line = assembler.readStringLine();
            LOG.info("Reading line: " + line);
            if ("> ".equals(line)) {
                String text = command.prompt(line);
                LOG.info("Sending  text: " + text);
                serialPort.writeString(text);
                serialPort.writeString("\u001A");
                line = assembler.readStringLine();
                LOG.info("Reading line: " + line);
            } else if (!StringUtils.isEmpty(line) && !FINAL_RESULT_CODE.contains(line)) {
                if (responseBuffer.length() > 0) responseBuffer.append('\n');
                responseBuffer.append(line);
            }
        } while (!FINAL_RESULT_CODE.contains(line));
        command.setStatus(line);
        return responseBuffer.toString();
    }

    public void close() throws SerialPortException {
        serialPort.removeEventListener();
        if (serialPort.isOpened()) {
            serialPort.closePort();
        }
    }


}

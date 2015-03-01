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
    private final String pin;

    public ATDevice(String serialPort, String pin) {
        this.serialPort = new SerialPort(serialPort);
        this.assembler = new SerialPortAssembler(this.serialPort);
        this.pin = pin;
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
        StringBuilder responseBuilder = new StringBuilder();
        LOG.info("Sending line: " + command.getCommand());
        serialPort.writeString(command.getCommand());
        serialPort.writeString("\r");

        String line;
        do {
            line = assembler.readStringLine();
            LOG.info("Reading line: " + line);
            if(CMEError.isCMEError(line)){
                CMEError error = CMEError.parse(line);
                if(error.isPinRequired()){
                    ATCommand enterPinCommand = new ATCommand("AT+CPIN=" + pin);
                    execute(enterPinCommand);
                    if ("OK".equals(enterPinCommand.getStatus())) {
                        return execute(command);
                    }
                }
                throw error;

            }
            if ("> ".equals(line)) {
                String text = command.prompt(line);
                LOG.info("Sending line: " + text);
                serialPort.writeString(text);
                serialPort.writeString("\u001A");
            } else if (!StringUtils.isEmpty(line) && !FINAL_RESULT_CODE.contains(line)) {
                if (responseBuilder.length() > 0) responseBuilder.append('\n');
                responseBuilder.append(line);
            }
        } while (!FINAL_RESULT_CODE.contains(line));
        command.setStatus(line);
        return responseBuilder.toString();
    }

    public void close() throws SerialPortException {
        serialPort.removeEventListener();
        if (serialPort.isOpened()) {
            serialPort.closePort();
        }
    }


}

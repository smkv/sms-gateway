package ee.smkv.sms.senders.at;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class SerialPortAssembler implements SerialPortEventListener {
    protected final StringBuffer buffer = new StringBuffer();
    protected final SerialPort serialPort;

    public SerialPortAssembler(SerialPort serialPort) {
        this.serialPort = serialPort;
    }

    @Override
    public void serialEvent(SerialPortEvent event) {
        if (event.isRXCHAR() && event.getEventValue() > 0) {
            String str = readBytesFromSerialPort(event.getEventValue());
            writeToBufferAndNotifyAll(str);
        }
    }

    protected synchronized void writeToBufferAndNotifyAll(String str) {
        buffer.append(str);
        notifyAll();
    }

    private String readBytesFromSerialPort(int count) {
        try {
            return serialPort.readString(count);
        } catch (SerialPortException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public synchronized String readStringLine() {
        int lineEndPos;
        while (!(buffer.indexOf("\r\n") >= 0 || buffer.indexOf("> ") >= 0)) {
            try {
                wait(10000); // wait until new bytes will received
            } catch (InterruptedException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }

        String line;
        lineEndPos = buffer.indexOf("\r\n");
        if (lineEndPos >= 0) {
            line = buffer.substring(0, lineEndPos);
        } else {
            lineEndPos = buffer.indexOf("> ");
            line = buffer.substring(0, lineEndPos + 2);
        }
        buffer.delete(0, lineEndPos + 2);
        return line;
    }

    @Override
    public String toString() {
        return "SerialPortAssembler{" +
                "serialPort=" + serialPort.getPortName() +
                ", buffer=" + buffer +
                '}';
    }
}

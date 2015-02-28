package ee.smkv.sms.senders.at;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class ATDevice implements SerialPortEventListener {
    

    private final SerialPort serialPort;
    private final Object waitObject = new Object();

    public ATDevice(String serialPort) {
        this.serialPort = new SerialPort(serialPort);
    }

    public void open() throws SerialPortException {
        if (!serialPort.isOpened()) {
            serialPort.openPort();
            serialPort.setParams(SerialPort.BAUDRATE_115200,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            serialPort.addEventListener(this);
        }
    }

    public void execute(ATCommand command) throws SerialPortException {
        synchronized (waitObject) {
            serialPort.writeBytes(command.getBytes());
            try {
                waitObject.wait(3000);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException("No any response from device in 3 sec.");
            }
            command.handleResponse(serialPort.readString());
        }
    }

    public void close() throws SerialPortException {
        if (serialPort.isOpened()) {
            serialPort.closePort();
        }
    }



    @Override
    public void serialEvent(SerialPortEvent event) {
        if(event.isRXCHAR() && event.getEventValue() > 0){
            synchronized (waitObject) {
                waitObject.notifyAll();
            }
        }
    }
}

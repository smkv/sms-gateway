package ee.smkv.sms.model;

public class SmsMessage {
    private String recipient;
    private String text;

    public SmsMessage() {
    }

    public SmsMessage(String recipient, String text) {
        this.recipient = recipient;
        this.text = text;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "SmsMessage{" +
                "recipient='" + recipient + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}

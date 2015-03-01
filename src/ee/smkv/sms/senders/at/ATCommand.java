package ee.smkv.sms.senders.at;

public class ATCommand {
    protected String command;
    protected String text;
    protected String status = "";


    public ATCommand(String command) {
        this.command = command;
    }

    public ATCommand(String command, String text) {
        this.command = command;
        this.text = text;
    }

    public String getCommand() {
        return command;
    }

    public String prompt(String response) {
        return text;
    }


    public String getStatus() {
        return status;
    }

    void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ATCommand{" +
                "status='" + status + '\'' +
                "command='" + command + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}

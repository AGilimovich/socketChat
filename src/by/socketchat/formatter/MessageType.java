package by.socketchat.formatter;

/**
 * Created by Администратор on 27.12.2016.
 */
public enum MessageType {
    AUTH(0), CHAT(1), CONTACTS(2), REGISTRATION(3);

    public int code;

    MessageType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}

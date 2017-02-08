package by.socketchat.protocol;

/**
 * Created by Администратор on 27.12.2016.
 */
public enum MessageType {
    AUTH(0), CHAT(1), CONTACTS(2), REGISTRATION(3), LOGOUT(4), COOKIES_AUTH(5);

    public int code;

    MessageType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static MessageType getType(String code) {
        switch (code) {
            case "0":
                return AUTH;
            case "1":
                return CHAT;
            case "2":
                return CONTACTS;
            case "3":
                return REGISTRATION;
            case "4":
                return LOGOUT;
            case "5":
                return COOKIES_AUTH;
        }
        return null;
    }

}

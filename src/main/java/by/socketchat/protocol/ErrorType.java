package by.socketchat.protocol;

/**
 * Created by Aleksandr on 02.02.2017.
 */
public enum ErrorType {
    INVALID_MESSAGE_FORMAT(0), INTERNAL_ERROR(1);

    public int code;

    ErrorType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

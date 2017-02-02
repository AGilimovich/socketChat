package by.socketchat.protocol;

/**
 * Created by Aleksandr on 02.02.2017.
 */
public enum ErrorType {
    CORRUPTED_MESSAGE(0), ALREADY_AUTHEENTICATED(1);

    public int code;

    ErrorType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

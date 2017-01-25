package by.socketchat.service.authentication;

/**
 * Created by Администратор on 22.12.2016.
 */
public enum AuthStatus {

    AUTHENTICATED(0), INVALID_CREDENTIALS(1);
    private final int code;

    AuthStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

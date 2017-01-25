package by.socketchat.service.registration;

/**
 * Created by Администратор on 22.12.2016.
 */
public enum RegistrationStatus {
    REGISTERED(0), ILLEGAL_PASSWORD(1), ILLEGAL_NAME(2), ILLEGAL_CREDENTIALS(3), NAME_EXISTS(4);
    private final int code;


    RegistrationStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

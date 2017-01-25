package by.socketchat.formatter.auth;

import by.socketchat.service.authentication.AuthStatus;

/**
 * Created by Администратор on 28.12.2016.
 */
public abstract class AbstractAuthFormatter {
    public abstract String format(AuthStatus status);
}

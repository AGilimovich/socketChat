package by.socketchat.formatter.registration;

import by.socketchat.service.registration.RegistrationStatus;
import org.springframework.stereotype.Service;

/**
 * Created by Администратор on 28.12.2016.
 */
public abstract class AbstractRegFormatter {
    public abstract String format(RegistrationStatus status);
}

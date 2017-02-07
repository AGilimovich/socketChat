package by.socketchat.service.validation;

import org.springframework.stereotype.Service;

/**
 * Created by Администратор on 22.12.2016.
 */
public interface
IValidationService {
    ValidationStatus validate(String name, String password);

    void setNamePattern(String namePattern);

    void setPasswordPattern(String passwordPattern);

}

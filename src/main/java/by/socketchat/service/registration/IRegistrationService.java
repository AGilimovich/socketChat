package by.socketchat.service.registration;

import by.socketchat.connection.IConnection;
import by.socketchat.dao.AbstractDao;
import by.socketchat.entity.message.request.registration.AbstractRegRequest;
import by.socketchat.entity.user.IUser;
import by.socketchat.formatter.registration.AbstractRegFormatter;
import by.socketchat.service.ServiceInitException;
import by.socketchat.service.validation.IValidationService;

/**
 * Created by Администратор on 22.12.2016.
 */
public interface IRegistrationService {
    RegistrationStatus register(IConnection connection, AbstractRegRequest request);

//    void setUserDao(AbstractDao<IUser> userDB);
//
//    void setFormatter(AbstractRegFormatter formatter);
//
//    void setValidator(IValidationService validator);
}

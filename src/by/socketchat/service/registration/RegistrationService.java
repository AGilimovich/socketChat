package by.socketchat.service.registration;

import by.socketchat.connection.IConnection;
import by.socketchat.dao.AbstractDao;
import by.socketchat.server.IServer;
import by.socketchat.entity.message.request.registration.AbstractRegRequest;
import by.socketchat.entity.user.IUser;
import by.socketchat.entity.user.User;
import by.socketchat.formatter.registration.AbstractRegFormatter;
import by.socketchat.service.validation.IValidationService;
import by.socketchat.utility.encoding.Encoder;

import java.io.IOException;

/**
 * Created by Администратор on 22.12.2016.
 */
public class RegistrationService implements IRegistrationService {
    private AbstractDao<IUser> userDao;
    private IValidationService validator;
    private AbstractRegFormatter formatter;
    private IServer dispatcher;

    public RegistrationService(IServer dispatcher, AbstractDao<IUser> userDao, AbstractRegFormatter formatter, IValidationService validator) {
        this.userDao = userDao;
        this.formatter = formatter;
        this.validator = validator;
        this.dispatcher = dispatcher;
    }

    @Override
    public RegistrationStatus register(IConnection connection, AbstractRegRequest request) {
        RegistrationStatus status = null;
        switch (validator.validate(request.getName(), request.getPassword())) {
            case VALID:
                if (userDao.findByName(request.getName()) == null) {
                    userDao.add(User.newUser(request.getName(), request.getPassword()));
                    status = RegistrationStatus.REGISTERED;
                } else status = RegistrationStatus.NAME_EXISTS;
                break;
            case ILLEGAL_NAME:
                status = RegistrationStatus.ILLEGAL_NAME;
                break;
            case ILLEGAL_PASSWORD:
                status = RegistrationStatus.ILLEGAL_PASSWORD;
                break;
            case ILLEGAL_CREDENTIALS:
                status = RegistrationStatus.ILLEGAL_CREDENTIALS;
                break;
            default:
                break;
        }


        try {
            connection.write(Encoder.encode(formatter.format(status)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;

    }


//    @Override
//    public void setUserDao(AbstractDao<IUser> userDB) {
//        this.userDao = userDB;
//    }
//
//    @Override
//    public void setValidator(IValidationService validator) {
//        this.validator = validator;
//    }
//
//    @Override
//    public void setFormatter(AbstractRegFormatter formatter) {
//        this.formatter = formatter;
//    }
}

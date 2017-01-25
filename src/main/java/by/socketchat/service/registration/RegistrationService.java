package by.socketchat.service.registration;

import by.socketchat.connection.IConnection;
import by.socketchat.dao.AbstractRepository;
import by.socketchat.entity.message.request.registration.AbstractRegRequest;
import by.socketchat.entity.user.User;
import by.socketchat.formatter.registration.AbstractRegFormatter;
import by.socketchat.server.Server;
import by.socketchat.service.validation.IValidationService;
import by.socketchat.utility.encoding.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by Администратор on 22.12.2016.
 */
@Service
public class RegistrationService implements IRegistrationService {
    private AbstractRepository<User> userRepository;
    private IValidationService validator;
    private AbstractRegFormatter formatter;
    private Server dispatcher;

    @Autowired
    public RegistrationService(Server dispatcher, AbstractRepository<User> userRepository, AbstractRegFormatter formatter, IValidationService validator) {
        this.userRepository = userRepository;
        this.formatter = formatter;
        this.validator = validator;
        this.dispatcher = dispatcher;
    }

    @Override
    public RegistrationStatus register(IConnection connection, AbstractRegRequest request) {
        RegistrationStatus status = null;
        switch (validator.validate(request.getLogin(), request.getPassword())) {
            case VALID:
                if (userRepository.findByLogin(request.getLogin()) == null) {
                    userRepository.save(new User(null, "name", request.getLogin(), request.getPassword(), null));//TODO
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

}

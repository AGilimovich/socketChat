package by.socketchat.service.registration;

import by.socketchat.connection.IConnection;
import by.socketchat.entity.message.request.registration.AbstractRegRequest;
import org.springframework.stereotype.Service;

/**
 * Created by Администратор on 22.12.2016.
 */
public interface IRegistrationService {
    RegistrationStatus register(IConnection connection, AbstractRegRequest request);

}

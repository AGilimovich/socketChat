package by.socketchat.service.authentication;

import by.socketchat.connection.IConnection;
import by.socketchat.entity.message.request.auth.AbstractAuthRequest;
import by.socketchat.session.ISession;
import org.springframework.stereotype.Service;

/**
 * Created by Администратор on 30.11.2016.
 */
public interface IAuthService {
    ISession authenticate(IConnection connection, AbstractAuthRequest request);

}

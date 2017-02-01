package by.socketchat.service.authentication;

import by.socketchat.connection.Connection;
import by.socketchat.entity.message.request.auth.AbstractAuthRequest;
import by.socketchat.session.ISession;

/**
 * Created by Администратор on 30.11.2016.
 */
public interface IAuthService {
    ISession authenticate(Connection connection, AbstractAuthRequest request);
    boolean logOut(ISession session);

}

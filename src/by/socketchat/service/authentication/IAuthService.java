package by.socketchat.service.authentication;

import by.socketchat.connection.IConnection;
import by.socketchat.dao.AbstractDao;
import by.socketchat.entity.message.request.auth.AbstractAuthRequest;
import by.socketchat.entity.user.IUser;
import by.socketchat.formatter.auth.AbstractAuthFormatter;
import by.socketchat.service.ServiceInitException;

/**
 * Created by Администратор on 30.11.2016.
 */
public interface IAuthService {
    AuthStatus authenticate(IConnection connection, AbstractAuthRequest request);






//    void setUserDao(AbstractDao<IUser> userDB);
//
//    void setFormatter(AbstractAuthFormatter formatter);
}

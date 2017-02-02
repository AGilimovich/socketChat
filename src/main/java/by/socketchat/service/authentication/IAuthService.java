package by.socketchat.service.authentication;

import by.socketchat.request.IRequest;
import by.socketchat.session.ISession;

/**
 * Created by Администратор on 30.11.2016.
 */
public interface IAuthService {
    ISession authenticate(IRequest request);

}

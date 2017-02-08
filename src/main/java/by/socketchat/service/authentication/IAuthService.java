package by.socketchat.service.authentication;

import by.socketchat.entity.session.Session;
import by.socketchat.request.IRequest;

/**
 * Created by Администратор on 30.11.2016.
 */
public interface IAuthService {
    Session authenticate(IRequest request);

}

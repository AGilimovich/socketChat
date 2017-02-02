package by.socketchat.service.registration;

import by.socketchat.request.IRequest;

/**
 * Created by Администратор on 22.12.2016.
 */
public interface IRegistrationService {
    RegistrationStatus register(IRequest request);

}

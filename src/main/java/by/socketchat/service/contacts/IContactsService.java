package by.socketchat.service.contacts;

import by.socketchat.request.IRequest;

/**
 * Created by Aleksandr on 05.01.2017.
 */
public interface IContactsService {

    void updateUserContacts(IRequest request);

    void updateAllAuthenticatedUsersContacts();

}

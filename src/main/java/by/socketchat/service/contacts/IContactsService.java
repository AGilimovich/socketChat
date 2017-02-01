package by.socketchat.service.contacts;

import by.socketchat.connection.IConnection;
import by.socketchat.session.ISession;
import org.springframework.stereotype.Service;

/**
 * Created by Aleksandr on 05.01.2017.
 */
public interface IContactsService {

    void updateUserContacts(ISession session);

    void updateAllAuthenticatedUsersContacts();

}

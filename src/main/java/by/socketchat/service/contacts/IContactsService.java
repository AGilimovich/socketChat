package by.socketchat.service.contacts;

import by.socketchat.connection.IConnection;

/**
 * Created by Aleksandr on 05.01.2017.
 */
public interface IContactsService {

    void updateUserContacts(IConnection connection);

    void updateAllAuthenticatedUsersContacts();

//    void setUserDao(AbstractDao<IUser> userDao);
//
//    void setFormatter(AbstractContactsFormatter formatter);

}

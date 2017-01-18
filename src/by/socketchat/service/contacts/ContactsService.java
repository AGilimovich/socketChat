package by.socketchat.service.contacts;

import by.socketchat.connection.IConnection;
import by.socketchat.dao.AbstractDao;
import by.socketchat.server.IServer;
import by.socketchat.entity.user.IUser;
import by.socketchat.formatter.contacts.AbstractContactsFormatter;
import by.socketchat.utility.encoding.Encoder;

import java.io.IOException;

/**
 * Created by Администратор on 06.01.2017.
 */
public class ContactsService implements IContactsService {
    private IServer dispatcher;
    private AbstractContactsFormatter formatter;

    //DAO
    private AbstractDao<IUser> userDao;

    public ContactsService(IServer dispatcher, AbstractContactsFormatter formatter, AbstractDao<IUser> userDao) {
        this.dispatcher = dispatcher;
        this.formatter = formatter;
        this.userDao = userDao;
    }


    @Override
    public void updateUserContacts(IConnection connection) {
        try {
            connection.write(Encoder.encode(formatter.format(dispatcher.getAuthenticatedConnections().values())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAllAuthenticatedUsersContacts() {
        for (IConnection connection : dispatcher.getAuthenticatedConnections().keySet()) {
            try {
                connection.write(Encoder.encode(formatter.format(dispatcher.getAuthenticatedConnections().values())));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

//    @Override
//    public void setUserDao(AbstractDao<IUser> userDao) {
//        this.userDao = userDao;
//    }
//
//    @Override
//    public void setFormatter(AbstractContactsFormatter formatter) {
//        this.formatter = formatter;
//    }
}

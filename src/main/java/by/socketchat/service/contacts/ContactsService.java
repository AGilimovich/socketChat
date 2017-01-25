package by.socketchat.service.contacts;

import by.socketchat.connection.IConnection;
import by.socketchat.dao.AbstractDao;
import by.socketchat.server.IServer;
import by.socketchat.entity.user.IUser;
import by.socketchat.formatter.contacts.AbstractContactsFormatter;
import by.socketchat.utility.encoding.Encoder;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Администратор on 06.01.2017.
 */
public class ContactsService implements IContactsService {
    private IServer server;
    private AbstractContactsFormatter formatter;


    //DAO
    private AbstractDao<IUser> userDao;

    public ContactsService(IServer server, AbstractContactsFormatter formatter, AbstractDao<IUser> userDao) {
        this.server = server;
        this.formatter = formatter;
        this.userDao = userDao;
    }


    @Override
    public void updateUserContacts(IConnection connection) {
        Collection<IUser> contacts = new HashSet<IUser>();
        IUser requester = server.getUserForConnection(connection);

        for (IUser contact : server.getAuthenticatedUsers()) {
            if (requester != contact)
                contact.addContact(contact);
        }

        try {
            connection.write(Encoder.encode(formatter.format(contacts)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAllAuthenticatedUsersContacts() {
        for (IConnection connection : server.getAuthenticatedConnections().keySet()) {
            Collection<IUser> contacts = new HashSet<IUser>();
            IUser requester = server.getUserForConnection(connection);
            for (IUser contact : server.getAuthenticatedUsers()) {
                if (requester != contact)
                    contacts.add(contact);
            }

            try {
                    System.out.println(formatter.format(contacts));//TODO delete
                    connection.write(Encoder.encode(formatter.format(contacts)));
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

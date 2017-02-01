package by.socketchat.service.contacts;

import by.socketchat.connection.IConnection;
import by.socketchat.dao.AbstractRepository;
import by.socketchat.entity.user.User;
import by.socketchat.formatter.contacts.AbstractContactsFormatter;
import by.socketchat.server.Server;
import by.socketchat.session.ISession;
import by.socketchat.utility.encoding.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Администратор on 06.01.2017.
 */
@Service
public class ContactsService implements IContactsService {
    private Server server;
    private AbstractContactsFormatter formatter;


    //DAO
    private AbstractRepository<User> userDao;


    @Autowired
    public ContactsService(Server server, AbstractContactsFormatter formatter, AbstractRepository<User> userDao) {
        this.server = server;
        this.formatter = formatter;
        this.userDao = userDao;
    }


    @Override
    public void updateUserContacts(ISession session) {
//TODO
    }

    @Override
    public void updateAllAuthenticatedUsersContacts() {
        Set<ISession> sessions = server.getSessions();


        for (ISession session : sessions) {
            Collection<User> contacts = new HashSet<User>();
            User requester = session.getUser();

            for (User contact : server.getAuthenticatedUsersSet()) {
                if (!requester.equals(contact))
                    contacts.add(contact);
            }

            try {
                session.getConnection().write(Encoder.encode(formatter.format(contacts)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}

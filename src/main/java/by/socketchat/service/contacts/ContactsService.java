package by.socketchat.service.contacts;

import by.socketchat.entity.session.Session;
import by.socketchat.entity.user.User;
import by.socketchat.protocol.IMessageFormatter;
import by.socketchat.request.IRequest;
import by.socketchat.server.Server;
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
    private IMessageFormatter formatter;




    @Autowired
    public ContactsService(Server server, IMessageFormatter formatter) {
        this.server = server;
        this.formatter = formatter;
    }


    @Override
    public void updateUserContacts(IRequest request) {

//TODO
    }

    @Override
    public void updateAllAuthenticatedUsersContacts() {
        Set<Session> sessions = server.getSessions();


        for (Session session : sessions) {
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

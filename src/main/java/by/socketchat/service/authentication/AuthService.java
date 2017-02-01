package by.socketchat.service.authentication;


import by.socketchat.connection.IConnection;
import by.socketchat.dao.AbstractRepository;
import by.socketchat.entity.message.request.auth.AbstractAuthRequest;
import by.socketchat.entity.user.User;
import by.socketchat.formatter.auth.AbstractAuthFormatter;
import by.socketchat.server.Server;
import by.socketchat.session.AbstractSessionFactory;
import by.socketchat.session.ISession;
import by.socketchat.utility.encoding.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Администратор on 30.11.2016.
 */
@Service
public class AuthService implements IAuthService {
    private AbstractRepository<User> userDao;
    private AbstractAuthFormatter formatter;
    private Server server;
    private AbstractSessionFactory sessionFactory;


    @Autowired
    public AuthService(AbstractRepository<User> userDao, AbstractAuthFormatter formatter, Server server) {
        this.userDao = userDao;
        this.formatter = formatter;
        this.server = server;
    }

    @Override
    public ISession authenticate(IConnection connection, AbstractAuthRequest request) {
        List<User> users = userDao.getAll();
        if (users.isEmpty()) {
            try {
                connection.write(Encoder.encode(formatter.format(AuthStatus.INVALID_CREDENTIALS)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        Iterator<User> it = users.iterator();
        User u = null;


        while (it.hasNext()) {
            if ((u = it.next()).getLogin().equals(request.getName())) {
                if (u.getPassword().equals(request.getPassword())) {
                    try {
                        connection.write(Encoder.encode(formatter.format(AuthStatus.AUTHENTICATED)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return sessionFactory.buildSession(u, connection);
                }

            }
        }
        try {
            connection.write(Encoder.encode(formatter.format(AuthStatus.INVALID_CREDENTIALS)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Autowired
    public void setSessionFactory(AbstractSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}

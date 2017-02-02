package by.socketchat.service.authentication;


import by.socketchat.connection.IConnection;
import by.socketchat.entity.message.AuthMessage;
import by.socketchat.entity.user.User;
import by.socketchat.protocol.IMessageFormatter;
import by.socketchat.repository.AbstractRepository;
import by.socketchat.request.IRequest;
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
    private IMessageFormatter formatter;
    private AbstractSessionFactory sessionFactory;


    @Autowired
    public AuthService(AbstractRepository<User> userDao, IMessageFormatter formatter) {
        this.userDao = userDao;
        this.formatter = formatter;
    }

    @Override
    public ISession authenticate(IRequest request) {
        IConnection connection = request.getConnection();
        AuthMessage message = (AuthMessage) request.getMessage();
        List<User> users = userDao.getAll();
        if (users.isEmpty()) {
            try {
                connection.write(Encoder.encode(formatter.format(AuthStatus.INVALID_CREDENTIALS, null)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        Iterator<User> it = users.iterator();
        User u = null;


        while (it.hasNext()) {
            if ((u = it.next()).getLogin().equals(message.getName())) {
                if (u.getPassword().equals(message.getPassword())) {
                    try {
                        connection.write(Encoder.encode(formatter.format(AuthStatus.AUTHENTICATED, u)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return sessionFactory.buildSession(u, connection);
                }

            }
        }
        try {
            connection.write(Encoder.encode(formatter.format(AuthStatus.INVALID_CREDENTIALS, null)));
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

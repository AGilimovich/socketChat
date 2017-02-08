package by.socketchat.service.authentication;


import by.socketchat.connection.IConnection;
import by.socketchat.entity.message.AuthMessage;
import by.socketchat.entity.message.CookiesAuthMessage;
import by.socketchat.entity.session.Session;
import by.socketchat.entity.user.User;
import by.socketchat.protocol.IMessageFormatter;
import by.socketchat.repository.AbstractRepository;
import by.socketchat.request.IRequest;
import by.socketchat.entity.session.AbstractSessionFactory;
import by.socketchat.utility.encoding.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;


@Service
public class AuthService implements IAuthService {
    private AbstractRepository<User> userRepository;
    private IMessageFormatter formatter;
    private AbstractSessionFactory sessionFactory;
    private AbstractRepository<Session> sessionRepository;

    @Autowired
    public AuthService(AbstractRepository<User> userRepository, IMessageFormatter formatter) {
        this.userRepository = userRepository;
        this.formatter = formatter;
    }


    @Override
    public Session authenticate(IRequest request) {
        IConnection connection = request.getConnection();

        List<User> users = userRepository.getAll();

        /*If no users in db -> cannot authenticate*/
        if (users.isEmpty()) {
            try {
                connection.write(Encoder.encode(formatter.format(AuthStatus.INVALID_CREDENTIALS, null)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        /*If requested authentication using session cookies*/
        if (request.getMessage() instanceof CookiesAuthMessage) {
            return authenticateByCookies(request);
        }

        AuthMessage message = (AuthMessage) request.getMessage();
        Iterator<User> it = users.iterator();
        User u = null;
        while (it.hasNext()) {
            if ((u = it.next()).getLogin().equals(message.getLogin())) {
                if (u.getPassword().equals(message.getPassword())) {
                    Session session = sessionFactory.buildSession(u, connection, message.isRememberMe());
                    try {
                        connection.write(Encoder.encode(formatter.format(AuthStatus.AUTHENTICATED, session)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    return session;
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


    private Session authenticateByCookies(IRequest request) {
        Session session = null;
        CookiesAuthMessage message = (CookiesAuthMessage) request.getMessage();
        if ((session = sessionRepository.findByUuid(message.getUuid())) != null) {
            session.setConnection(request.getConnection());
        }


        return session;
    }


    @Autowired
    public void setSessionFactory(AbstractSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Autowired
    public void setSessionRepository(AbstractRepository<Session> sessionRepository) {
        this.sessionRepository = sessionRepository;
    }
}

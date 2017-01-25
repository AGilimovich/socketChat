package by.socketchat.service.authentication;


import by.socketchat.connection.ConnectionState;
import by.socketchat.connection.IConnection;
import by.socketchat.dao.AbstractRepository;
import by.socketchat.entity.message.request.auth.AbstractAuthRequest;
import by.socketchat.entity.user.User;
import by.socketchat.formatter.auth.AbstractAuthFormatter;
import by.socketchat.server.Server;
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



    @Autowired
    public AuthService(AbstractRepository<User> userDao, AbstractAuthFormatter formatter, Server server) {
        this.userDao = userDao;
        this.formatter = formatter;
        this.server = server;
    }

    @Override
    public AuthStatus authenticate(IConnection connection, AbstractAuthRequest request) {
        List<User> users = userDao.getAll();
        if (users.isEmpty()) {
            try {
                connection.write(Encoder.encode(formatter.format(AuthStatus.INVALID_CREDENTIALS)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return AuthStatus.INVALID_CREDENTIALS;
        }
        Iterator<User> it = users.iterator();
        User u = null;


        while (it.hasNext()) {
            if ((u = it.next()).getLogin().equals(request.getName())) {
                if (u.getPassword().equals(request.getPassword())) {
                    server.addAuthenticatedConnection(connection, u);


                    try {
                        connection.write(Encoder.encode(formatter.format(AuthStatus.AUTHENTICATED)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    connection.setState(ConnectionState.AUTHENTICATED);
                    return AuthStatus.AUTHENTICATED;
                }

            }
        }
        try {
            connection.write(Encoder.encode(formatter.format(AuthStatus.INVALID_CREDENTIALS)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return AuthStatus.INVALID_CREDENTIALS;

    }


}

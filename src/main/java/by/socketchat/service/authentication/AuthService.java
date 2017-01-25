package by.socketchat.service.authentication;


import by.socketchat.connection.ConnectionState;
import by.socketchat.connection.IConnection;
import by.socketchat.dao.AbstractDao;
import by.socketchat.entity.message.request.auth.AbstractAuthRequest;
import by.socketchat.entity.user.IUser;
import by.socketchat.formatter.auth.AbstractAuthFormatter;
import by.socketchat.server.IServer;
import by.socketchat.utility.encoding.Encoder;

import java.io.IOException;
import java.util.*;

/**
 * Created by Администратор on 30.11.2016.
 */
public class AuthService implements IAuthService {
    private AbstractDao<IUser> userDao;
    private AbstractAuthFormatter formatter;
    private IServer server;

    private Map<IConnection, IUser> authenticatedConnections;
    private Set<IUser> authenticatedUsers;


    public AuthService(AbstractDao<IUser> userDao, AbstractAuthFormatter formatter, IServer server) {
        authenticatedConnections = new HashMap<IConnection, IUser>();
        authenticatedUsers = new HashSet<IUser>();
        this.userDao = userDao;
        this.formatter = formatter;
        this.server = server;
    }

    @Override
    public AuthStatus authenticate(IConnection connection, AbstractAuthRequest request) {
        Set<IUser> users = userDao.getAll();
        if (users.isEmpty()) {
            try {
                connection.write(Encoder.encode(formatter.format(AuthStatus.INVALID_CREDENTIALS)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return AuthStatus.INVALID_CREDENTIALS;
        }
        Iterator<IUser> it = users.iterator();
        IUser u = null;


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

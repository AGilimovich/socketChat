package by.socketchat.server;

import by.socketchat.connection.AbstractConnection;
import by.socketchat.connection.IConnection;
import by.socketchat.entity.user.User;
import by.socketchat.session.ISession;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 * Created by Администратор on 29.11.2016.
 */
public interface IServer {

    void start(int port);

    void start();


    void onMessage(IConnection connection, byte[] message);

    void closeConnection(AbstractConnection con);

    Set<ISession> getSessions();

    Set<ISession> findSession(User user);

    ISession findSession(IConnection connection);

    Set<User> getAuthenticatedUsersSet();


}

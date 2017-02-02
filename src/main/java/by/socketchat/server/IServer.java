package by.socketchat.server;

import by.socketchat.connection.Connection;
import by.socketchat.connection.IConnection;
import by.socketchat.entity.user.User;
import by.socketchat.request.IRequest;
import by.socketchat.session.ISession;

import java.util.Set;

/**
 * Created by Администратор on 29.11.2016.
 */
public interface IServer {

    void start(int port);

    void start();


    void onMessage(Connection connection, byte[] message);

    void closeConnection(Connection con);

    Set<ISession> getSessions();

    Set<ISession> findSession(User user);

    ISession findSession(Connection connection);

    Set<User> getAuthenticatedUsersSet();

//    void authenticate(IRequest request);


}

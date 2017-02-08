package by.socketchat.server;

import by.socketchat.connection.Connection;
import by.socketchat.entity.session.Session;
import by.socketchat.entity.user.User;

import java.util.Set;
import java.util.UUID;

/**
 * Created by Администратор on 29.11.2016.
 */
public interface IServer {

    void start(int port);

    void start();


    void handleMessage(Connection connection, byte[] message);

    void closeConnection(Connection con);

    Set<Session> getSessions();

    Set<Session> findSession(User user);

    Session findSession(Connection connection);

    Session findSession(UUID uuid);

    Set<User> getAuthenticatedUsersSet();


}

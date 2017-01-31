package by.socketchat.server;

import by.socketchat.connection.AbstractConnection;
import by.socketchat.connection.IConnection;
import by.socketchat.entity.user.User;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 * Created by Администратор on 29.11.2016.
 */
public interface IServer {

    void start();

    void setPort(int port);

    void closeConnection(AbstractConnection con);

    void onMessage(IConnection connection, byte[] message);

    Map<IConnection, User> getAuthenticatedConnections();

    void addAuthenticatedConnection(IConnection connection, User user);

    void removeAuthenticatedConnection(IConnection connection);

    Set<User> getAuthenticatedUsers();

    Set<IConnection>  getUserConnection(User user);

    User getUserForConnection(IConnection connection);

}

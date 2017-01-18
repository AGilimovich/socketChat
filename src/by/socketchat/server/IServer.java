package by.socketchat.server;

import by.socketchat.connection.AbstractConnection;
import by.socketchat.connection.IConnection;
import by.socketchat.entity.user.IUser;

import java.util.Map;

/**
 * Created by Администратор on 29.11.2016.
 */
public interface IServer {

    void start();

    // void onConnect(IConnection con);

    void closeConnection(AbstractConnection con);

//        void onMessage(IConnection connection, String message);
    void onMessage(IConnection connection, byte[] message);

    Map<IConnection, IUser> getAuthenticatedConnections();

    void addAuthenticatedConnection(IConnection connection, IUser user);

    void removeAuthenticatedConnection(IConnection connection);

    IConnection getUserConnection(IUser user);

}
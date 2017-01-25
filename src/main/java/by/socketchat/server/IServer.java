package by.socketchat.server;

import by.socketchat.connection.AbstractConnection;
import by.socketchat.connection.IConnection;
import by.socketchat.entity.user.IUser;

import java.util.Map;
import java.util.Set;

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

//    void addAuthenticatedUser(IUser user);
//
//    void removeAuthenticatedUser(IUser user);

    Set<IUser> getAuthenticatedUsers ();

    IConnection getUserConnection(IUser user);

    IUser getUserForConnection(IConnection connection);

}

package by.socketchat.connection.connector;

import by.socketchat.connection.IConnection;

/**
 * Created by Aleksandr on 19.12.2016.
 */
public interface IConnector {
    boolean connect(IConnection connection);
}

package by.socketchat.connection;

import by.socketchat.server.IServer;
import org.springframework.stereotype.Component;

import java.net.Socket;

/**
 * Created by Администратор on 03.01.2017.
 */

public abstract class AbstractConnectionFactory {
    public abstract AbstractConnection getConnection(IServer server, Socket socket);


}

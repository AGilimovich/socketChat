package by.socketchat.connection;

import by.socketchat.connection.connector.IConnector;
import by.socketchat.server.IServer;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Администратор on 03.01.2017.
 */
public class WebSocketConnectionFactory extends AbstractConnectionFactory {
    IConnector connector;

    public WebSocketConnectionFactory(IConnector connector) {
        this.connector = connector;
    }

    @Override
    public AbstractConnection getConnection(IServer server, Socket socket) {
        if (server == null || socket == null) {
            return null;
        }
        WebSocketConnection con = null;
        try {
            con = new WebSocketConnection(server, socket);
            if (!connector.connect(con)) {
                con.close();
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return con;
    }
}

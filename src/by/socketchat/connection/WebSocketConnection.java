package by.socketchat.connection;

import by.socketchat.server.IServer;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Администратор on 13.12.2016.
 */
public class WebSocketConnection extends AbstractConnection {
    private IServer server;
    private ConnectionState connectionState;

    public WebSocketConnection(IServer server, Socket socket) throws IOException {
        super(socket);
        this.server = server;
        connectionState = ConnectionState.CONNECTED;
    }

//    private void onCreate() throws IOException {
//        server.onConnect(this);
//    }

    @Override
    public void close() {
        try {
            onDestroy();
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.closeConnection(this);
    }


    private void checkIncomingMessage() {
//        String message = null;
        byte[] bytes = null;
        try {
            if ((bytes = readEncoded()) != null) {

                server.onMessage(this, bytes);
            } else {
                close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void checkAlive() {
        if (readLine() == null) {
            close();
        }
    }

    @Override
    public void setState(ConnectionState connectionState) {
        this.connectionState = connectionState;
    }

    public ConnectionState getConnectionState() {
        return connectionState;
    }

    public void run() {
        String message = null;
        while (true) {
            // checkAlive();
            checkIncomingMessage();
        }

    }
}

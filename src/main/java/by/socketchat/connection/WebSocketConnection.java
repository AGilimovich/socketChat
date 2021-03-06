package by.socketchat.connection;

import by.socketchat.server.IServer;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Администратор on 13.12.2016.
 */
public class WebSocketConnection extends Connection {
    private IServer server;

    public WebSocketConnection(IServer server, Socket socket) throws IOException {
        super(socket);
        this.server = server;
    }



    @Override
    public void close() {
        try {
            super.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.closeConnection(this);
    }


    private void checkIncomingMessage() {
        byte[] bytes = null;
        try {
            if ((bytes = readEncoded()) != null) {

                server.handleMessage(this, bytes);
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




    public void run() {
        String message = null;
        while (true) {
            checkIncomingMessage();
        }

    }
}

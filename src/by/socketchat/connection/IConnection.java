package by.socketchat.connection;

import java.io.IOException;

/**
 * Created by Администратор on 29.11.2016.
 */
public interface IConnection {

    void write(byte[] message) throws IOException;

    String read() throws IOException;
    byte[] readEncoded() throws IOException;

    boolean isActive();

    void close();

    ConnectionState getConnectionState();

    void setState(ConnectionState connectionState);

    int getDEFAULT_BUF_SIZE();

    void setDEFAULT_BUF_SIZE(int DEFAULT_BUF_SIZE);
}

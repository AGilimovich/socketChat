package by.socketchat.connection;

import java.io.IOException;

/**
 * Created by Администратор on 29.11.2016.
 */
public interface IConnection {

    void write(byte[] message) throws IOException;

    String read() throws IOException;

    void close()throws IOException;

}

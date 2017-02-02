package by.socketchat.request;

import by.socketchat.connection.IConnection;
import by.socketchat.entity.message.IMessage;
import by.socketchat.protocol.MessageType;

/**
 * Created by Aleksandr on 02.02.2017.
 */
public interface IRequest {

    IMessage getMessage();

    MessageType getType();

    IConnection getConnection();
}

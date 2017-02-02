package by.socketchat.request;

import by.socketchat.connection.IConnection;
import by.socketchat.entity.message.IMessage;
import by.socketchat.protocol.MessageType;

/**
 * Created by Aleksandr on 02.02.2017.
 */
public class Request implements IRequest {
    private IMessage message;
    private IConnection connection;

    public Request(IConnection connection, IMessage message) {
        this.connection = connection;
        this.message = message;
    }


    @Override
    public IMessage getMessage() {
        return message;
    }

    @Override
    public MessageType getType() {
        return message.getType();
    }

    @Override
    public IConnection getConnection() {
        return connection;
    }
}

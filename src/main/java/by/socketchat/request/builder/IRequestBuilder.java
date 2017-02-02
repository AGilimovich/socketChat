package by.socketchat.request.builder;

import by.socketchat.connection.IConnection;
import by.socketchat.entity.message.IMessage;
import by.socketchat.request.IRequest;

/**
 * Created by Aleksandr on 02.02.2017.
 */
public interface IRequestBuilder {
    IRequest buildRequest(IConnection connection, IMessage message);
}

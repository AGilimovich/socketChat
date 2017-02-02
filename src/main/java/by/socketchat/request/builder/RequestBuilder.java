package by.socketchat.request.builder;

import by.socketchat.connection.IConnection;
import by.socketchat.entity.message.IMessage;
import by.socketchat.request.IRequest;
import by.socketchat.request.Request;
import org.springframework.stereotype.Component;

/**
 * Created by Aleksandr on 02.02.2017.
 */

@Component
public class RequestBuilder implements IRequestBuilder {

    @Override
    public IRequest buildRequest(IConnection connection, IMessage message) {
        if (connection == null || message == null) {
            return null;
        }
        return new Request(connection, message);
    }


}

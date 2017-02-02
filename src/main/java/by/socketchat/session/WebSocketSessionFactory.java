package by.socketchat.session;

import by.socketchat.connection.IConnection;
import by.socketchat.entity.user.User;
import org.springframework.stereotype.Component;

/**
 * Created by Aleksandr on 01.02.2017.
 */
@Component
public class WebSocketSessionFactory implements AbstractSessionFactory {
    @Override
    public ISession buildSession(User user, IConnection connection) {
        return new WebSocketSession(user, connection);
    }
}

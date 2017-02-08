package by.socketchat.entity.session;

import by.socketchat.connection.IConnection;
import by.socketchat.entity.user.User;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by Aleksandr on 01.02.2017.
 */
@Component
public class WebSocketSessionFactory implements AbstractSessionFactory {

    @Override
    public Session buildSession(User user, IConnection connection, boolean storeSession) {
        return new Session(UUID.randomUUID(), user, connection, storeSession);
    }


}

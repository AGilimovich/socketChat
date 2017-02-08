package by.socketchat.entity.session;

import by.socketchat.connection.IConnection;
import by.socketchat.entity.user.User;

/**
 * Created by Aleksandr on 01.02.2017.
 */
public interface AbstractSessionFactory {
    Session buildSession(User user, IConnection connection, boolean storeSession);
}

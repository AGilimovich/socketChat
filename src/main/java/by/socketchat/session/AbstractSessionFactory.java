package by.socketchat.session;

import by.socketchat.connection.Connection;
import by.socketchat.entity.user.User;

/**
 * Created by Aleksandr on 01.02.2017.
 */
public interface AbstractSessionFactory {
    ISession buildSession(User user, Connection connection);
}

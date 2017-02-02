package by.socketchat.session;

import by.socketchat.connection.IConnection;
import by.socketchat.entity.user.User;

import java.util.Date;

/**
 * Created by Aleksandr on 01.02.2017.
 */
public interface ISession {
    User getUser();

    IConnection getConnection();

    Date getStartingTime();


}

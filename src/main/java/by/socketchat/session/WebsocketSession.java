package by.socketchat.session;

import by.socketchat.connection.IConnection;
import by.socketchat.entity.user.User;

import java.util.Date;

/**
 * Created by Aleksandr on 01.02.2017.
 */

public class WebSocketSession implements ISession {
    private User user;
    private IConnection connection;
    private Date startingTime;

    public WebSocketSession(User user, IConnection connection) {
        this.user = user;
        this.connection = connection;
        startingTime = new Date();
    }


    @Override
    public User getUser() {
        return user;
    }

    @Override
    public IConnection getConnection() {
        return connection;
    }

    @Override
    public Date getStartingTime() {
        return startingTime;
    }


}

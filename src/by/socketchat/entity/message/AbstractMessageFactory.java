package by.socketchat.entity.message;

import by.socketchat.connection.IConnection;
import by.socketchat.dao.AbstractDao;
import by.socketchat.entity.message.chat.AbstractChatMessage;
import by.socketchat.entity.message.request.auth.AbstractAuthRequest;
import by.socketchat.entity.message.request.contacts.AbstractContactsRequest;
import by.socketchat.entity.message.request.registration.AbstractRegRequest;
import by.socketchat.entity.user.IUser;
import by.socketchat.server.IServer;

import java.util.Properties;

/**
 * Created by Администратор on 28.12.2016.
 */
public abstract class AbstractMessageFactory {

    public abstract AbstractAuthRequest newAuthRequest(Properties properties);

    public abstract AbstractRegRequest newRegRequest(Properties properties);

    public abstract AbstractContactsRequest newContactsRequest(IUser user, Properties properties);

    public abstract AbstractChatMessage newChatMessage(IConnection connection, Properties properties);

    public abstract void setServer(IServer server);

    public abstract void setUserDao(AbstractDao<IUser> userDao);
}

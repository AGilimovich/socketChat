package by.socketchat.entity.message;

import by.socketchat.connection.IConnection;
import by.socketchat.dao.AbstractDao;
import by.socketchat.entity.message.chat.ChatMessage;
import by.socketchat.entity.message.request.auth.AbstractAuthRequest;
import by.socketchat.entity.message.request.contacts.AbstractContactsRequest;
import by.socketchat.entity.message.request.registration.AbstractRegRequest;
import by.socketchat.entity.user.User;
import by.socketchat.server.IServer;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * Created by Администратор on 28.12.2016.
 */
@Component
public interface AbstractMessageBuilder {

    AbstractAuthRequest newAuthRequest(Properties properties);

    AbstractRegRequest newRegRequest(Properties properties);

    AbstractContactsRequest newContactsRequest(User user, Properties properties);

    ChatMessage newChatMessage(IConnection connection, Properties properties);

    void setServer(IServer server);

    void setUserDao(AbstractDao<User> userDao);
    void setMessageDao(AbstractDao<ChatMessage> messageDaoDao);
}

package by.socketchat.entity.message;

import by.socketchat.connection.IConnection;
import by.socketchat.entity.message.chat.ChatMessage;
import by.socketchat.entity.message.request.auth.AbstractAuthRequest;
import by.socketchat.entity.message.request.contacts.AbstractContactsRequest;
import by.socketchat.entity.message.request.registration.AbstractRegRequest;
import by.socketchat.entity.user.User;
import by.socketchat.session.ISession;

import java.util.Properties;

/**
 * Created by Администратор on 28.12.2016.
 */
public interface AbstractMessageBuilder {

    AbstractAuthRequest buildAuthRequest(Properties properties);

    AbstractRegRequest buildRegRequest(Properties properties);

    AbstractContactsRequest buildContactsRequest(User user, Properties properties);

    ChatMessage buildChatMessage(User sender, Properties properties);

}

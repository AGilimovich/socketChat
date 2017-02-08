package by.socketchat.entity.message.builder;

import by.socketchat.entity.message.*;

import java.util.Properties;

/**
 * Created by Aleksandr on 02.02.2017.
 */
public interface IMessageBuilder {
    ChatMessage buildChatMessage(Properties properties);

    AuthMessage buildAuthMessage(Properties properties);

    RegMessage buildRegMessage(Properties properties);

    ContactsMessage buildContactsMessage(Properties properties);

    LogoutMessage buildLogOutMessage(Properties properties);

    CookiesAuthMessage buildAutoAuthMessage(Properties properties);

}

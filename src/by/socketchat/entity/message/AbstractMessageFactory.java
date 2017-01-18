package by.socketchat.entity.message;

import by.socketchat.entity.message.chat.AbstractChatMessage;
import by.socketchat.entity.message.request.auth.AbstractAuthRequest;
import by.socketchat.entity.message.request.contacts.AbstractContactsRequest;
import by.socketchat.entity.message.request.registration.AbstractRegRequest;
import by.socketchat.entity.user.IUser;

import java.util.Properties;

/**
 * Created by Администратор on 28.12.2016.
 */
public abstract class AbstractMessageFactory {

    public abstract AbstractAuthRequest newAuthRequest(Properties properties);

    public abstract AbstractRegRequest newRegRequest(Properties properties);

    public abstract AbstractContactsRequest newContactsRequest(IUser user, Properties properties);

    public abstract AbstractChatMessage newChatMessage(Properties properties);

}

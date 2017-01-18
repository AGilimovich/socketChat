package by.socketchat.formatter;

import by.socketchat.formatter.auth.AbstractAuthFormatter;
import by.socketchat.formatter.chat.AbstractChatFormatter;
import by.socketchat.formatter.registration.AbstractRegFormatter;
import by.socketchat.formatter.contacts.AbstractContactsFormatter;

/**
 * Created by Aleksandr on 08.01.2017.
 */
public abstract class AbstractFormatterFactory {
    public abstract AbstractAuthFormatter getAuthFormatter();

    public abstract AbstractChatFormatter getChatFormatter();

    public abstract AbstractContactsFormatter getContactsFormatter();

    public abstract AbstractRegFormatter getRegFormatter();

}

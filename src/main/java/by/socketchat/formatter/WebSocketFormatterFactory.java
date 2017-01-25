package by.socketchat.formatter;

import by.socketchat.formatter.auth.AbstractAuthFormatter;
import by.socketchat.formatter.auth.AuthFormatter;
import by.socketchat.formatter.chat.AbstractChatFormatter;
import by.socketchat.formatter.chat.ChatFormatter;
import by.socketchat.formatter.contacts.AbstractContactsFormatter;
import by.socketchat.formatter.contacts.ContactsFormatter;
import by.socketchat.formatter.registration.AbstractRegFormatter;
import by.socketchat.formatter.registration.RegistrationFormatter;

/**
 * Created by Aleksandr on 08.01.2017.
 */
public class WebSocketFormatterFactory implements AbstractFormatterFactory {

    @Override
    public AbstractAuthFormatter getAuthFormatter() {
        return new AuthFormatter();
    }

    @Override
    public AbstractChatFormatter getChatFormatter() {
        return new ChatFormatter();
    }

    @Override
    public AbstractContactsFormatter getContactsFormatter() {
        return new ContactsFormatter();
    }

    @Override
    public AbstractRegFormatter getRegFormatter() {
        return new RegistrationFormatter();
    }
}

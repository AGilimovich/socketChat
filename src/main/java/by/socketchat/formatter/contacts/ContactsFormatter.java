package by.socketchat.formatter.contacts;

import by.socketchat.entity.user.User;
import by.socketchat.formatter.MessageType;
import by.socketchat.utility.json.Json;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

/**
 * Created by Администратор on 27.12.2016.
 */
@Service
public class ContactsFormatter extends AbstractContactsFormatter {
    @Override
    public String format(Collection<User> users) {
        Properties props = new Properties();
        Iterator<User> it = users.iterator();

        props.setProperty("type", String.valueOf(MessageType.CONTACTS.getCode()));
        props.setProperty("user", Json.stringify(users));
        return Json.stringify(props);
    }
}




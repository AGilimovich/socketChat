package by.socketchat.formatter;

import by.socketchat.entity.message.chat.ChatMessage;
import by.socketchat.entity.user.User;
import by.socketchat.service.authentication.AuthStatus;
import by.socketchat.service.registration.RegistrationStatus;
import by.socketchat.utility.json.Json;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

/**
 * Created by Aleksandr on 01.02.2017.
 */

@Component
public class MessageFormatter implements IMessageFormatter {

    private final DateFormat timeFormat = new SimpleDateFormat("DD.MM.YYYY HH:mm:ss");


    @Override
    public String format(RegistrationStatus status) {
        Properties props = new Properties();
        props.setProperty("type", String.valueOf(MessageType.REGISTRATION.getCode()));
        props.setProperty("status", String.valueOf(status.getCode()));
        return Json.stringify(props);
    }


    @Override
    public String format(ChatMessage message) {
        Properties props = new Properties();
        props.setProperty("type", String.valueOf(MessageType.CHAT.getCode()));
        props.setProperty("sender", String.valueOf(message.getSender().getLogin()));
        props.setProperty("time", timeFormat.format(message.getSendTime()));
        props.setProperty("content", message.getContent());
        return Json.stringify(props);
    }

    @Override
    public String format(AuthStatus status) {
        Properties props = new Properties();
        props.setProperty("type", String.valueOf(MessageType.AUTH.getCode()));
        props.setProperty("status", String.valueOf(status.getCode()));
        return Json.stringify(props);
    }

    @Override
    public String format(Collection<User> users) {
        Properties props = new Properties();
        Iterator<User> it = users.iterator();
        props.setProperty("type", String.valueOf(MessageType.CONTACTS.getCode()));
        props.setProperty("user", Json.stringify(users));
        return Json.stringify(props);
    }
}

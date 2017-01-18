package by.socketchat.formatter.chat;

import by.socketchat.entity.message.chat.AbstractChatMessage;
import by.socketchat.utility.json.Json;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;

/**
 * Created by Администратор on 27.12.2016.
 */
public class ChatFormatter extends AbstractChatFormatter {
    private final DateFormat timeFormat = new SimpleDateFormat("DD.MM.YYYY HH:mm:ss");


    @Override
    public String format(AbstractChatMessage message) {
        Properties props = new Properties();
        props.setProperty("sender", String.valueOf(message.getSender().getId()));
        props.setProperty("time", timeFormat.format(message.getTime()));
        props.setProperty("content", message.getContent());
        return Json.stringify(props);
    }
}



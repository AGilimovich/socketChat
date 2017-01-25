package by.socketchat.formatter.chat;

import by.socketchat.entity.message.chat.ChatMessage;
import by.socketchat.formatter.MessageType;
import by.socketchat.utility.json.Json;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;

/**
 * Created by Администратор on 27.12.2016.
 */
@Service
public class ChatFormatter extends AbstractChatFormatter {
    private final DateFormat timeFormat = new SimpleDateFormat("DD.MM.YYYY HH:mm:ss");


    @Override
    public String format(ChatMessage message) {
        Properties props = new Properties();
        props.setProperty("type", String.valueOf(MessageType.CHAT.getCode()));
        props.setProperty("sender", String.valueOf(message.getSender().getLogin()));
        props.setProperty("time", timeFormat.format(message.getSendTime()));
        props.setProperty("content", message.getContent());
        return Json.stringify(props);
    }
}




package by.socketchat.formatter.chat;

import by.socketchat.entity.message.chat.ChatMessage;
import org.springframework.stereotype.Service;

/**
 * Created by Администратор on 28.12.2016.
 */
public abstract class AbstractChatFormatter {
    public abstract String format(ChatMessage message);

}

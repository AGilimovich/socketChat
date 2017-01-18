package by.socketchat.formatter.chat;

import by.socketchat.entity.message.chat.AbstractChatMessage;

/**
 * Created by Администратор on 28.12.2016.
 */
public abstract class AbstractChatFormatter {
    public abstract String format(AbstractChatMessage message);

}

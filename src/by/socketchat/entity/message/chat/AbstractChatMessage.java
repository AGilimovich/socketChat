package by.socketchat.entity.message.chat;

import by.socketchat.entity.user.IUser;
import by.socketchat.service.chat.IChatService;

import java.util.Date;

/**
 * Created by Администратор on 28.12.2016.
 */
public abstract class AbstractChatMessage {

    public abstract long getId();

    public abstract Date getTime();

    public abstract IUser getSender();

    public abstract IUser getReceiver();

    public abstract String getContent();

}

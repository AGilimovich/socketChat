package by.socketchat.entity.message.chat;

import by.socketchat.entity.user.IUser;
import by.socketchat.utility.idgenerator.IDGenerator;

import java.util.Date;

/**
 * Created by Администратор on 27.12.2016.
 */
public class ChatMessage extends AbstractChatMessage {

    private final long id;
    private final IUser sender;
    private final IUser receiver;
    private final String content;
    private final Date time;

    public ChatMessage(IUser sender, IUser receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.time = new Date();
        id = IDGenerator.generateMessageID();
    }


    @Override
    public long getId() {
        return id;
    }

    @Override
    public IUser getSender() {
        return sender;
    }

    @Override
    public IUser getReceiver() {
        return receiver;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public Date getTime() {
        return time;
    }


}

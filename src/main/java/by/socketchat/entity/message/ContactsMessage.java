package by.socketchat.entity.message;

import by.socketchat.entity.message.IMessage;
import by.socketchat.entity.user.User;
import by.socketchat.protocol.MessageType;

import java.util.Date;

/**
 * Created by Aleksandr on 05.01.2017.
 */
public class ContactsMessage implements IMessage {

    private long id;
    private Date time;
    private User user;
    private MessageType type;


    public ContactsMessage(User user) {
        type = MessageType.CONTACTS;
        this.user = user;
        id = 0; //TODO
        time = new Date();


    }

    public User getUser() {
        return user;
    }

    public long getId() {
        return 0;
    }

    public Date getTime() {
        return null;
    }


    @Override
    public MessageType getType() {
        return type;
    }
}

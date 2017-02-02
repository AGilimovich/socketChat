package by.socketchat.entity.message;

import by.socketchat.entity.message.IMessage;
import by.socketchat.protocol.MessageType;

import java.util.Date;

/**
 * Created by Aleksandr on 04.01.2017.
 */
public class RegMessage implements IMessage {


    private long id;
    private Date time;
    private String name;
    private String password;
    private MessageType type;


    public RegMessage(String name, String password) {
        type = MessageType.REGISTRATION;
        id = 0; // TODO
        time = new Date();
        this.name = name;
        this.password = password;
    }


    public long getId() {
        return id;
    }

    public String getLogin() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Date getTime() {
        return time;
    }


    @Override
    public MessageType getType() {
        return type;
    }
}

package by.socketchat.entity.message;


import by.socketchat.protocol.MessageType;

import java.util.Date;

/**
 * Created by Aleksandr on 04.01.2017.
 */
public class AuthMessage implements IMessage {
    private long id;
    private Date time;
    private String name;
    private String password;
    private MessageType type;


    public AuthMessage(String name, String password) {
        id = 0;//TODO
        type = MessageType.AUTH;
        time = new Date();
        this.name = name;
        this.password = password;
    }


    public long getId() {
        return id;
    }

    public String getName() {
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

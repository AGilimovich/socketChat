package by.socketchat.protocol;

import by.socketchat.entity.message.IMessage;

/**
 * Created by Aleksandr on 02.02.2017.
 */
public interface IMessageParser {
    IMessage parse (byte[] rawMessage);

}

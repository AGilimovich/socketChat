package by.socketchat.protocol;

import by.socketchat.entity.message.IMessage;
import by.socketchat.entity.message.builder.IMessageBuilder;
import by.socketchat.entity.message.builder.WebSocketMessageBuilder;
import by.socketchat.utility.encoding.Decoder;
import by.socketchat.utility.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * Created by Aleksandr on 02.02.2017.
 */

@Component
public class MessageParser implements IMessageParser {

    private IMessageBuilder messageBuilder;

    @Override
    public IMessage parse(byte[] rawMessage) {
        Properties properties = Json.parse(Decoder.decode(rawMessage));
        if (properties.isEmpty()) {
            return null;
        }
        MessageType type = null;
        if ((type = MessageType.getType(properties.getProperty("type"))) == null) {
            return null;
        }

        switch (type) {
            case AUTH:
                return messageBuilder.buildAuthMessage(properties);
            case CHAT:
                return messageBuilder.buildChatMessage(properties);
            case CONTACTS:
                return messageBuilder.buildContactsMessage(properties);
            case REGISTRATION:
                return messageBuilder.buildRegMessage(properties);
            case LOGOUT:
                return messageBuilder.buildLogOutMessage(properties);
            default:
                return null;

        }
    }

    @Autowired
    public void setMessageBuilder(WebSocketMessageBuilder messageBuilder) {
        this.messageBuilder = messageBuilder;
    }
}

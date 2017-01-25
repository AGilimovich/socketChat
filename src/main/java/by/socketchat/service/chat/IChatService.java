package by.socketchat.service.chat;

import by.socketchat.entity.message.chat.ChatMessage;
import org.springframework.stereotype.Service;

/**
 * Created by Aleksandr on 05.01.2017.
 */
public interface IChatService {
    void send(ChatMessage message);
}

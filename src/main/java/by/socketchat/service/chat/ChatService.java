package by.socketchat.service.chat;

import by.socketchat.connection.IConnection;
import by.socketchat.dao.AbstractRepository;
import by.socketchat.entity.message.chat.ChatMessage;
import by.socketchat.entity.user.User;
import by.socketchat.formatter.chat.AbstractChatFormatter;
import by.socketchat.server.Server;
import by.socketchat.utility.encoding.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by Aleksandr on 05.01.2017.
 */
@Service
public class ChatService implements IChatService {
    private AbstractRepository<User> userDao;
    private Server server;
    private AbstractChatFormatter formatter;

    @Autowired
    public ChatService(Server server, AbstractRepository<User> userDao, AbstractChatFormatter formatter) {
        this.server = server;
        this.userDao = userDao;
        this.formatter = formatter;
    }

    @Override
    public void send(ChatMessage message) {

        IConnection receiverCon = server.getUserConnection(message.getReceiver());
        if (receiverCon != null) {
            try {
                receiverCon.write(Encoder.encode(formatter.format(message)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


}

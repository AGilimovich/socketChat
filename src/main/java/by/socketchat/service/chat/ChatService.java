package by.socketchat.service.chat;

import by.socketchat.connection.IConnection;
import by.socketchat.repository.AbstractRepository;
import by.socketchat.entity.message.chat.ChatMessage;
import by.socketchat.entity.user.User;
import by.socketchat.formatter.IMessageFormatter;
import by.socketchat.server.Server;
import by.socketchat.session.ISession;
import by.socketchat.utility.encoding.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 * Created by Aleksandr on 05.01.2017.
 */
@Service
public class ChatService implements IChatService {
    private AbstractRepository<User> userDao;
    private Server server;
    private IMessageFormatter formatter;
    private LinkedList<ChatMessage> messageQueue = new LinkedList<ChatMessage>(); //TODO


    @Autowired
    public ChatService(Server server, AbstractRepository<User> userDao, IMessageFormatter formatter) {
        this.server = server;
        this.userDao = userDao;
        this.formatter = formatter;


    }

    @Override
    public void send(ChatMessage message) {
        Set<ISession> sessions = server.findSession(message.getReceiver());

        if (!sessions.isEmpty()) {
            Iterator<ISession> it = sessions.iterator();
            while (it.hasNext()) {
                IConnection connection = it.next().getConnection();
                try {
                    connection.write(Encoder.encode(formatter.format(message)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            messageQueue.add(message);//TODO
        }

    }

}

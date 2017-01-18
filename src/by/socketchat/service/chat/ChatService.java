package by.socketchat.service.chat;

import by.socketchat.connection.IConnection;
import by.socketchat.dao.AbstractDao;
import by.socketchat.server.IServer;
import by.socketchat.entity.message.chat.AbstractChatMessage;
import by.socketchat.entity.user.IUser;
import by.socketchat.formatter.chat.AbstractChatFormatter;
import by.socketchat.utility.encoding.Encoder;

import java.io.IOException;

/**
 * Created by Aleksandr on 05.01.2017.
 */
public class ChatService implements IChatService {
    private AbstractDao<IUser> userDao;
    private IServer server;
    private AbstractChatFormatter formatter;

    public ChatService(IServer server, AbstractDao<IUser> userDao, AbstractChatFormatter formatter) {
        this.server = server;
        this.userDao = userDao;
        this.formatter = formatter;
    }

    @Override
    public void send(AbstractChatMessage message) {

        IConnection receiverCon = server.getUserConnection(message.getReceiver());
        try {
            receiverCon.write(Encoder.encode(formatter.format(message)));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

//    @Override
//    public void setUserDao(AbstractDao<IUser> userDao) {
//        this.userDao = userDao;
//    }
//
//    @Override
//    public void setFormatter(AbstractChatFormatter formatter) {
//        this.formatter = formatter;
//    }
}

package by.socketchat.service.chat;

import by.socketchat.connection.IConnection;
import by.socketchat.dao.AbstractDao;
import by.socketchat.server.IServer;
import by.socketchat.entity.message.chat.AbstractChatMessage;
import by.socketchat.entity.user.IUser;
import by.socketchat.formatter.chat.AbstractChatFormatter;

import java.io.IOException;

/**
 * Created by Aleksandr on 05.01.2017.
 */
public class ChatService implements IChatService {
    private AbstractDao<IUser> userDao;
    private IServer dispatcher;
    private AbstractChatFormatter formatter;

    public ChatService(IServer dispatcher, AbstractDao<IUser> userDao, AbstractChatFormatter formatter) {
        this.dispatcher = dispatcher;
        this.userDao = userDao;
        this.formatter = formatter;
    }

    @Override
    public void send(AbstractChatMessage message) {

        IConnection receiverCon = dispatcher.getUserConnection(message.getReceiver());
        String text = formatter.format(message);
        try {
            receiverCon.write(text.getBytes("UTF-8"));
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

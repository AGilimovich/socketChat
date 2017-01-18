package by.socketchat.service.chat;

import by.socketchat.dao.AbstractDao;
import by.socketchat.entity.message.chat.AbstractChatMessage;
import by.socketchat.entity.user.IUser;
import by.socketchat.formatter.chat.AbstractChatFormatter;
import by.socketchat.service.ServiceInitException;

/**
 * Created by Aleksandr on 05.01.2017.
 */
public interface IChatService {
    void send(AbstractChatMessage message);

//    void setUserDao(AbstractDao<IUser> userDao);
//
//    void setFormatter(AbstractChatFormatter formatter);
}

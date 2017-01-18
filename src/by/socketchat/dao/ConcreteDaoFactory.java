package by.socketchat.dao;

import by.socketchat.dao.message.MessageDao;
import by.socketchat.dao.user.UserDao;
import by.socketchat.entity.message.chat.AbstractChatMessage;
import by.socketchat.entity.user.IUser;

/**
 * Created by Администратор on 27.12.2016.
 */
public class ConcreteDaoFactory extends AbstractDaoFactory {


    @Override
    public AbstractDao<AbstractChatMessage> getMessageDao() {
        return new MessageDao();
    }

    @Override
    public AbstractDao<IUser> getUserDao() {
        return new UserDao();
    }
}

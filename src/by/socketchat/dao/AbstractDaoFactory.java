package by.socketchat.dao;

import by.socketchat.entity.message.chat.AbstractChatMessage;
import by.socketchat.entity.user.IUser;

/**
 * Created by Администратор on 27.12.2016.
 */
public abstract class AbstractDaoFactory {

    public abstract AbstractDao<AbstractChatMessage> getMessageDao();

    public abstract AbstractDao<IUser> getUserDao();


}

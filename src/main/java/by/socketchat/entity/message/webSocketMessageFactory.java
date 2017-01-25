package by.socketchat.entity.message;

import by.socketchat.connection.IConnection;
import by.socketchat.dao.AbstractDao;
import by.socketchat.entity.message.chat.AbstractChatMessage;
import by.socketchat.entity.message.chat.ChatMessage;
import by.socketchat.entity.message.request.auth.AbstractAuthRequest;
import by.socketchat.entity.message.request.auth.AuthRequest;
import by.socketchat.entity.message.request.contacts.AbstractContactsRequest;
import by.socketchat.entity.message.request.contacts.ContactsRequest;
import by.socketchat.entity.message.request.registration.AbstractRegRequest;
import by.socketchat.entity.message.request.registration.RegRequest;
import by.socketchat.entity.user.IUser;
import by.socketchat.server.IServer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by Администратор on 28.12.2016.
 */
public class webSocketMessageFactory implements AbstractMessageFactory {
    private final String timePattern = "DD.MM.YYY HH:mm:ss";
    private DateFormat format = new SimpleDateFormat(timePattern);
    private AbstractDao<IUser> userDao;
    private IServer server;

    @Override
    public void setServer(IServer server) {
        this.server = server;
    }

    @Override
    public void setUserDao(AbstractDao<IUser> userDao) {
        this.userDao = userDao;
    }


    @Override
    public AbstractChatMessage newChatMessage(IConnection connection, Properties properties) {
        if (userDao == null || server == null) {
            return null;
        }

        Date time = new Date();
        //IUser sender = userDao.findById(Long.parseLong(properties.getProperty("sender")));
        IUser sender = server.getUserForConnection(connection);
        IUser receiver = userDao.findById(Long.parseLong(properties.getProperty("receiver")));
        String content = properties.getProperty("content");

        return new ChatMessage(sender, receiver, content);
    }

    @Override
    public AbstractAuthRequest newAuthRequest(Properties properties) {
        Date time = new Date();
        String name = properties.getProperty("name");
        String password = properties.getProperty("password");


        return new AuthRequest(name, password);
    }

    @Override
    public AbstractRegRequest newRegRequest(Properties properties) {
        Date time = new Date();
//        try {
//            time = format.parse(properties.getProperty("time"));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        String name = properties.getProperty("name");
        String password = properties.getProperty("password");


        return new RegRequest(name, password);
    }

    @Override
    public AbstractContactsRequest newContactsRequest(IUser user, Properties properties) {
        Date time = new Date();
//        try {
//            time = format.parse(properties.getProperty("time"));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        return new ContactsRequest(user);
    }


}

package by.socketchat.entity.message;

import by.socketchat.connection.IConnection;
import by.socketchat.dao.AbstractDao;
import by.socketchat.entity.message.chat.ChatMessage;
import by.socketchat.entity.message.request.auth.AbstractAuthRequest;
import by.socketchat.entity.message.request.auth.AuthRequest;
import by.socketchat.entity.message.request.contacts.AbstractContactsRequest;
import by.socketchat.entity.message.request.contacts.ContactsRequest;
import by.socketchat.entity.message.request.registration.AbstractRegRequest;
import by.socketchat.entity.message.request.registration.RegRequest;
import by.socketchat.entity.user.User;
import by.socketchat.server.IServer;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Created by Администратор on 28.12.2016.
 */
public class WebSocketMessageBuilder implements AbstractMessageBuilder {
    private final String timePattern = "DD.MM.YYY HH:mm:ss";
    private DateFormat format = new SimpleDateFormat(timePattern);
    private AbstractDao<User> userDao;
    private AbstractDao<ChatMessage> messageDao;
    private IServer server;

    @Override
    @Autowired
    public void setServer(IServer server) {
        this.server = server;
    }

    @Override
    @Autowired
    public void setUserDao(AbstractDao<User> userDao) {
        this.userDao = userDao;
    }


    @Override
    @Autowired
    public void setMessageDao(AbstractDao<ChatMessage> messageDao) {
        this.messageDao = messageDao;
    }

    @Override
    public ChatMessage newChatMessage(IConnection connection, Properties properties) {
        if (userDao == null || server == null) {
            return null;
        }

        Date time = new Date();
        User sender = server.getUserForConnection(connection);
        User receiver = userDao.findById(Long.parseLong(properties.getProperty("receiver")));
        String content = properties.getProperty("content");

        return messageDao.save(new ChatMessage());
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
    public AbstractContactsRequest newContactsRequest(User user, Properties properties) {
        Date time = new Date();
//        try {
//            time = format.parse(properties.getProperty("time"));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        return new ContactsRequest(user);
    }


}

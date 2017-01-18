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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Created by Администратор on 28.12.2016.
 */
public class ConcreteMessageFactory extends AbstractMessageFactory {
    private final String timePattern = "DD.MM.YYY HH:mm:ss";
    private DateFormat format = new SimpleDateFormat(timePattern);
    private AbstractDao<IUser> userDao;

    @Override
    public AbstractChatMessage newChatMessage(Properties properties) {
        Date time = new Date();
//        try {
//            time = format.parse(properties.getProperty("time"));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        IUser sender = userDao.findById(Long.parseLong(properties.getProperty("sender")));
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

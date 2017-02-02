package by.socketchat.entity.message.builder;

import by.socketchat.entity.message.*;
import by.socketchat.repository.AbstractRepository;
import by.socketchat.entity.user.User;
import by.socketchat.server.IServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Created by Администратор on 28.12.2016.
 */
@Component
public class WebSocketMessageBuilder implements IMessageBuilder {
    private final String timePattern = "DD.MM.YYY HH:mm:ss";
    private DateFormat format = new SimpleDateFormat(timePattern);
    private AbstractRepository<User> userDao;
    private AbstractRepository<ChatMessage> messageRepository;
    private IServer server;

    @Autowired
    public void setServer(IServer server) {
        this.server = server;
    }

    @Autowired
    public void setUserDao(AbstractRepository<User> userDao) {
        this.userDao = userDao;
    }


    @Autowired
    public void setMessageRepository(AbstractRepository<ChatMessage> messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public ChatMessage buildChatMessage(Properties properties) {
        if (userDao == null || server == null) {
            return null;
        }
        User sender = userDao.findById(Long.parseLong(properties.getProperty("sender")));
        User receiver = userDao.findById(Long.parseLong(properties.getProperty("receiver")));
        String content = properties.getProperty("content");

        return messageRepository.save(new ChatMessage(null, sender, receiver, content, null));
    }

    @Override
    public AuthMessage buildAuthMessage(Properties properties) {
        Date time = new Date();
        String name = properties.getProperty("name");
        String password = properties.getProperty("password");


        return new AuthMessage(name, password);
    }

    @Override
    public RegMessage buildRegMessage(Properties properties) {
        Date time = new Date();


        String name = properties.getProperty("name");
        String password = properties.getProperty("password");


        return new RegMessage(name, password);
    }

    @Override
    public ContactsMessage buildContactsMessage(Properties properties) {
        Date time = new Date();
        User sender = userDao.findById(Long.parseLong(properties.getProperty("sender")));

        return new ContactsMessage(sender);
    }

    @Override
    public LogOutMessage buildLogOutMessage(Properties properties) {
        Date time = new Date();
        User sender = userDao.findById(Long.parseLong(properties.getProperty("sender")));

        return new LogOutMessage(sender);
    }


}

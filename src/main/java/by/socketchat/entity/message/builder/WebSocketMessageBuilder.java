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
    private AbstractRepository<AuthMessage> authMessageRepository;
    private AbstractRepository<RegMessage> regMessageRepository;
    private AbstractRepository<LogoutMessage> logoutMessageRepository;
    private AbstractRepository<ContactsMessage> contactsMessageRepository;

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

    @Autowired
    public void setAuthMessageRepository(AbstractRepository<AuthMessage> authMessageRepository) {
        this.authMessageRepository = authMessageRepository;
    }

    @Autowired
    public void setRegMessageRepository(AbstractRepository<RegMessage> regMessageRepository) {
        this.regMessageRepository = regMessageRepository;
    }

    @Autowired
    public void setLogoutMessageRepository(AbstractRepository<LogoutMessage> logoutMessageRepository) {
        this.logoutMessageRepository = logoutMessageRepository;
    }

    @Autowired
    public void setContactsMessageRepository(AbstractRepository<ContactsMessage> contactsMessageRepository) {
        this.contactsMessageRepository = contactsMessageRepository;
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
        String login = properties.getProperty("login");
        String password = properties.getProperty("password");


        return authMessageRepository.save(new AuthMessage(null, null, login, password));
    }

    @Override
    public RegMessage buildRegMessage(Properties properties) {


        String login = properties.getProperty("login");
        String password = properties.getProperty("password");
        return regMessageRepository.save(new RegMessage(null, null, login, password));
    }

    @Override
    public ContactsMessage buildContactsMessage(Properties properties) {

        User sender = userDao.findById(Long.parseLong(properties.getProperty("sender")));

        return contactsMessageRepository.save(new ContactsMessage(null, null, sender));

    }

    @Override
    public LogoutMessage buildLogOutMessage(Properties properties) {
        User sender = userDao.findById(Long.parseLong(properties.getProperty("sender")));

        return logoutMessageRepository.save(new LogoutMessage(null, null, sender));

    }


}

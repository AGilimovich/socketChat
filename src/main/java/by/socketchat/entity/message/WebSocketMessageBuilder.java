package by.socketchat.entity.message;

import by.socketchat.repository.AbstractRepository;
import by.socketchat.entity.message.chat.ChatMessage;
import by.socketchat.entity.message.request.auth.AbstractAuthRequest;
import by.socketchat.entity.message.request.auth.AuthRequest;
import by.socketchat.entity.message.request.contacts.AbstractContactsRequest;
import by.socketchat.entity.message.request.contacts.ContactsRequest;
import by.socketchat.entity.message.request.registration.AbstractRegRequest;
import by.socketchat.entity.message.request.registration.RegRequest;
import by.socketchat.entity.user.User;
import by.socketchat.server.IServer;
import by.socketchat.session.ISession;
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
public class WebSocketMessageBuilder implements AbstractMessageBuilder {
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
    public ChatMessage buildChatMessage(ISession session, Properties properties) {
        if (userDao == null || server == null) {
            return null;
        }

        User sender = session.getUser();
        User receiver = userDao.findById(Long.parseLong(properties.getProperty("receiver")));
        String content = properties.getProperty("content");

        return messageRepository.save(new ChatMessage(null, sender, receiver, content, null));
    }

    @Override
    public AbstractAuthRequest buildAuthRequest(Properties properties) {
        Date time = new Date();
        String name = properties.getProperty("name");
        String password = properties.getProperty("password");


        return new AuthRequest(name, password);
    }

    @Override
    public AbstractRegRequest buildRegRequest(Properties properties) {
        Date time = new Date();


        String name = properties.getProperty("name");
        String password = properties.getProperty("password");


        return new RegRequest(name, password);
    }

    @Override
    public AbstractContactsRequest buildContactsRequest(User user, Properties properties) {
        Date time = new Date();

        return new ContactsRequest(user);
    }


}

package by.socketchat.server;

import by.socketchat.connection.AbstractConnection;
import by.socketchat.connection.AbstractConnectionFactory;
import by.socketchat.connection.IConnection;
import by.socketchat.dao.AbstractRepository;
import by.socketchat.entity.message.AbstractMessageBuilder;
import by.socketchat.entity.message.chat.ChatMessage;
import by.socketchat.entity.user.User;
import by.socketchat.formatter.auth.AbstractAuthFormatter;
import by.socketchat.formatter.chat.AbstractChatFormatter;
import by.socketchat.formatter.contacts.AbstractContactsFormatter;
import by.socketchat.formatter.registration.AbstractRegFormatter;
import by.socketchat.service.authentication.IAuthService;
import by.socketchat.service.chat.IChatService;
import by.socketchat.service.contacts.IContactsService;
import by.socketchat.service.registration.IRegistrationService;
import by.socketchat.session.ISession;
import by.socketchat.session.SessionStatus;
import by.socketchat.utility.encoding.Decoder;
import by.socketchat.utility.json.Json;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

/**
 * Created by Администратор on 29.11.2016.
 */

public class Server implements IServer {
    private final int DEFAULT_PORT = 8080;
    //FACTORIES
    private AbstractMessageBuilder messageBuilder;

    //REPOSITORIES
    private AbstractRepository<ChatMessage> messageRepository;
    private AbstractRepository<User> userRepository;
    //SERVICES
    private AbstractConnectionFactory connectionFactory;
    private IRegistrationService registrationService;
    private IAuthService authenticationService;
    private IContactsService contactsService;
    private IChatService chatService;

    //Active sessions
    private Set<ISession> sessions;


    @Autowired
    public void setMessageBuilder(AbstractMessageBuilder messageBuilder) {
        this.messageBuilder = messageBuilder;
    }

    @Autowired
    public void setMessageRepository(AbstractRepository<ChatMessage> messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Autowired
    public void setUserRepository(AbstractRepository<User> userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setConnectionFactory(AbstractConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Autowired
    public void setRegistrationService(IRegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @Autowired
    public void setAuthenticationService(IAuthService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Autowired
    public void setContactsService(IContactsService contactsService) {
        this.contactsService = contactsService;
    }

    @Autowired
    public void setChatService(IChatService chatService) {
        this.chatService = chatService;
    }


    public Server() {
        sessions = new HashSet<ISession>();
    }



    @Override
    public synchronized void closeConnection(AbstractConnection con) {
        closeSession(findSession(con));
        if (con.isAlive()) {
            con.stop();
        }
    }

    @Override
    public void onMessage(IConnection connection, byte[] message) {
        Properties properties = Json.parse(Decoder.decode(message));
        if (properties.isEmpty()) {
            return;
        }
        ISession session = findSession(connection);

        switch (properties.getProperty("type")) {
            case "0":
                if ((session = authenticationService.authenticate(connection, messageBuilder.buildAuthRequest(properties))) != null) {
                    sessions.add(session);
                    contactsService.updateAllAuthenticatedUsersContacts();
                }
                break;
            case "1":
                if (session != null) {
                    chatService.send(messageBuilder.buildChatMessage(session, properties));
                }
                break;
            case "2":
                if (session != null) {
                    contactsService.updateUserContacts(session);
                }
                break;
            case "3":
                registrationService.register(connection, messageBuilder.buildRegRequest(properties));
                break;
            case "4":
                connection.close();
            default:
                return;//TODO message format exception

        }


    }

    @Override
    public Set<ISession> getSessions() {
        return sessions;
    }


    @Override
    public Set<ISession> findSession(User user) {
        Set<ISession> ss = new HashSet<ISession>();
        Iterator<ISession> it = sessions.iterator();
        ISession session = null;
        while (it.hasNext()) {
            if ((session = it.next()).getUser().equals(user)) {
                ss.add(session);
            }
        }
        return ss;
    }

    @Override
    public ISession findSession(IConnection connection) {
        ISession session = null;
        Iterator<ISession> it = sessions.iterator();
        while (it.hasNext()) {
            if ((session = it.next()).getConnection() == connection) {
                return session;
            }

        }
        return null;
    }

    @Override
    public Set<User> getAuthenticatedUsersSet() {
        Set<User> users = new HashSet<User>();
        Iterator<ISession> it = sessions.iterator();
        while (it.hasNext()) {
            users.add(it.next().getUser());
        }
        return users;
    }

    private void closeSession(ISession session) {
        sessions.remove(session);
    }

    @Override
    public void start(int port) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();

        }
        if (serverSocket != null) {
            while (true) {
                Socket client = null;
                try {
                    client = serverSocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                AbstractConnection con = connectionFactory.getConnection(this, client);
                if (con != null) {
                    con.start();
                }
            }
        }
    }

    @Override
    public void start() {
        start(DEFAULT_PORT);
    }


}

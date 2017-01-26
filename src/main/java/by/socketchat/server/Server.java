package by.socketchat.server;

import by.socketchat.connection.AbstractConnection;
import by.socketchat.connection.AbstractConnectionFactory;
import by.socketchat.connection.ConnectionState;
import by.socketchat.connection.IConnection;
import by.socketchat.dao.AbstractRepository;
import by.socketchat.entity.message.AbstractMessageBuilder;
import by.socketchat.entity.message.chat.ChatMessage;
import by.socketchat.entity.user.User;
import by.socketchat.formatter.auth.AbstractAuthFormatter;
import by.socketchat.formatter.chat.AbstractChatFormatter;
import by.socketchat.formatter.contacts.AbstractContactsFormatter;
import by.socketchat.formatter.registration.AbstractRegFormatter;
import by.socketchat.service.authentication.AuthStatus;
import by.socketchat.service.authentication.IAuthService;
import by.socketchat.service.chat.IChatService;
import by.socketchat.service.contacts.IContactsService;
import by.socketchat.service.registration.IRegistrationService;
import by.socketchat.utility.encoding.Decoder;
import by.socketchat.utility.json.Json;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * Created by Администратор on 29.11.2016.
 */

public class Server implements IServer {
    private int port;
    //FACTORIES
    private AbstractMessageBuilder messageBuilder;
    //DAO
    private AbstractRepository<ChatMessage> messageDao;
    private AbstractRepository<User> userDao;
    //SERVICES
    private AbstractConnectionFactory connectionFactory;
    private IRegistrationService registrationService;
    private IAuthService authenticationService;
    private IContactsService contactsService;
    private IChatService chatService;
    private AbstractRegFormatter regFormatter;
    private AbstractAuthFormatter authFormatter;
    private AbstractChatFormatter chatFormatter;
    private AbstractContactsFormatter contactsFormatter;
    //Server Cache
    private Map<IConnection, User> authenticatedConnections;
    private Set<User> authenticatedUsers;


    @Autowired
    public void setMessageBuilder(AbstractMessageBuilder messageBuilder) {
        this.messageBuilder = messageBuilder;
    }

    @Autowired
    public void setMessageDao(AbstractRepository<ChatMessage> messageDao) {
        this.messageDao = messageDao;
    }

    @Autowired
    public void setUserDao(AbstractRepository<User> userDao) {
        this.userDao = userDao;
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

    @Autowired
    public void setRegFormatter(AbstractRegFormatter regFormatter) {
        this.regFormatter = regFormatter;
    }

    @Autowired
    public void setAuthFormatter(AbstractAuthFormatter authFormatter) {
        this.authFormatter = authFormatter;
    }

    @Autowired
    public void setChatFormatter(AbstractChatFormatter chatFormatter) {
        this.chatFormatter = chatFormatter;
    }

    @Autowired
    public void setContactsFormatter(AbstractContactsFormatter contactsFormatter) {
        this.contactsFormatter = contactsFormatter;
    }




    public Server() {
        authenticatedConnections = new HashMap<IConnection, User>();
        authenticatedUsers = new HashSet<User>();
    }

    @Override
    public void setPort(int port) {
        this.port = port;
    }


    @Override
    public synchronized void closeConnection(AbstractConnection con) {
        removeAuthenticatedConnection(con);
        if (con.isAlive()) {
            con.stop();
        }
    }

    @Override
    public void onMessage(IConnection connection, byte[] message) {


        String decoded = Decoder.decode(message);
        System.out.println(decoded);
        Properties properties = Json.parse(decoded);
        if (properties.isEmpty()) {
            return;
        }
        switch (properties.getProperty("type")) {
            case "0":

                if (authenticationService.authenticate(connection, messageBuilder.buildAuthRequest(properties)) == AuthStatus.AUTHENTICATED) {
                    contactsService.updateAllAuthenticatedUsersContacts();
                }
                break;
            case "1":
                if (connection.getConnectionState() == ConnectionState.AUTHENTICATED) {
                    chatService.send(messageBuilder.buildChatMessage(connection, properties));
                }
                break;
            case "2":
                if (connection.getConnectionState() == ConnectionState.AUTHENTICATED) {
                    contactsService.updateUserContacts(connection);
                }
                contactsService.updateUserContacts(connection);
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
    public Map<IConnection, User> getAuthenticatedConnections() {
        return authenticatedConnections;
    }


    @Override
    public void addAuthenticatedConnection(IConnection connection, User user) {
        authenticatedConnections.put(connection, user);
        if (!authenticatedUsers.contains(user)) {
            authenticatedUsers.add(user);
        }

    }

    @Override
    public void removeAuthenticatedConnection(IConnection connection) {
        User user = getUserForConnection(connection);
        authenticatedUsers.remove(user);
        authenticatedConnections.remove(connection);
        contactsService.updateAllAuthenticatedUsersContacts();

    }

    @Override
    public Set<User> getAuthenticatedUsers() {
        return authenticatedUsers;
    }


    @Override
    public IConnection getUserConnection(User user) {
        Iterator<Map.Entry<IConnection, User>> iterator = authenticatedConnections.entrySet().iterator();
        Map.Entry<IConnection, User> entry = null;
        while (iterator.hasNext()) {
            if ((entry = iterator.next()).getValue().equals(user)) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public User getUserForConnection(IConnection connection) {
        return authenticatedConnections.get(connection);
    }

    public void start() {
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


}

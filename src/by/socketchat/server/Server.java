package by.socketchat.server;

import by.socketchat.connection.AbstractConnection;
import by.socketchat.connection.AbstractConnectionFactory;
import by.socketchat.connection.ConnectionState;
import by.socketchat.connection.IConnection;
import by.socketchat.dao.AbstractDao;
import by.socketchat.dao.AbstractDaoFactory;
import by.socketchat.entity.message.AbstractMessageFactory;
import by.socketchat.entity.message.chat.AbstractChatMessage;
import by.socketchat.entity.user.IUser;
import by.socketchat.entity.user.User;
import by.socketchat.formatter.AbstractFormatterFactory;
import by.socketchat.formatter.auth.AbstractAuthFormatter;
import by.socketchat.formatter.chat.AbstractChatFormatter;
import by.socketchat.formatter.contacts.AbstractContactsFormatter;
import by.socketchat.formatter.registration.AbstractRegFormatter;
import by.socketchat.service.AbstractServiceFactory;
import by.socketchat.service.ServiceInitException;
import by.socketchat.service.authentication.AuthStatus;
import by.socketchat.service.authentication.IAuthService;
import by.socketchat.service.chat.IChatService;
import by.socketchat.service.contacts.IContactsService;
import by.socketchat.service.registration.IRegistrationService;
import by.socketchat.utility.encoding.Decoder;
import by.socketchat.utility.json.Json;
import by.socketchat.utility.logger.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Администратор on 29.11.2016.
 */
public class Server implements IServer {
    private final int port;

    //FACTORIES
    private AbstractMessageFactory messageFactory;
    private AbstractServiceFactory serviceFactory;
    private AbstractDaoFactory daoFactory;
    private AbstractFormatterFactory formatterFactory;

    //DAO
    private AbstractDao<AbstractChatMessage> messageDao;
    private AbstractDao<IUser> userDao;
    //SERVICES
    private AbstractConnectionFactory connectionFactory;
    private IRegistrationService registrationService;
    private IAuthService authenticationService;
    private IContactsService contactsService;
    private IChatService chatService;


    //LOGGER
    private Logger logger;

    private AbstractRegFormatter regFormatter;
    private AbstractAuthFormatter authFormatter;
    private AbstractChatFormatter chatFormatter;
    private AbstractContactsFormatter contactsFormatter;


    private Map<IConnection, IUser> authenticatedConnections;

    public Server(final int port, AbstractServiceFactory serviceFactory, AbstractDaoFactory daoFactory, AbstractMessageFactory messageFactory, AbstractFormatterFactory formatterFactory, AbstractConnectionFactory connectionFactory) {
        this.port = port;

        //FACTORIES
        this.messageFactory = messageFactory;

        this.serviceFactory = serviceFactory;
        this.formatterFactory = formatterFactory;
        this.daoFactory = daoFactory;
        authenticatedConnections = new HashMap<IConnection, IUser>();
        this.connectionFactory = connectionFactory;

    }

    private void init() {
        //DAO
        userDao = daoFactory.getUserDao();
        messageDao = daoFactory.getMessageDao();
        userDao.add(User.newUser("Alex", "1"));
        userDao.add(User.newUser("Charlie", "1"));
        userDao.add(User.newUser("Pam", "1"));

        //FORMATTERS
        regFormatter = formatterFactory.getRegFormatter();
        chatFormatter = formatterFactory.getChatFormatter();
        authFormatter = formatterFactory.getAuthFormatter();
        contactsFormatter = formatterFactory.getContactsFormatter();

        //SERVICE FACTORY INIT
        serviceFactory.setServer(this);
        serviceFactory.setUserDao(userDao);
        serviceFactory.setAuthFormatter(authFormatter);
        serviceFactory.setChatFormatter(chatFormatter);
        serviceFactory.setContactsFormatter(contactsFormatter);
        serviceFactory.setRegFormatter(regFormatter);

        //MESSAGE FACTORY INIT
        messageFactory.setServer(this);
        messageFactory.setUserDao(userDao);

        //SERVICES
        try {
            authenticationService = serviceFactory.getAuthService();
            registrationService = serviceFactory.getRegService();
            chatService = serviceFactory.getChatService();
            contactsService = serviceFactory.getContactsService();
        } catch (ServiceInitException e) {
            e.printStackTrace();
        }


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

                if (authenticationService.authenticate(connection, messageFactory.newAuthRequest(properties)) == AuthStatus.AUTHENTICATED) {
                    contactsService.updateAllAuthenticatedUsersContacts();
                }
                break;
            case "1":
                if (connection.getConnectionState() == ConnectionState.AUTHENTICATED) {
                    chatService.send(messageFactory.newChatMessage(connection, properties));
                }
                break;
            case "2":
                if (connection.getConnectionState() == ConnectionState.AUTHENTICATED) {
                    contactsService.updateUserContacts(connection);
                }
                contactsService.updateUserContacts(connection);
                break;
            case "3":
                registrationService.register(connection, messageFactory.newRegRequest(properties));
                break;

        }


    }

    @Override
    public Map<IConnection, IUser> getAuthenticatedConnections() {
        return authenticatedConnections;
    }


    @Override
    public void addAuthenticatedConnection(IConnection connection, IUser user) {
        authenticatedConnections.put(connection, user);
    }

    @Override
    public void removeAuthenticatedConnection(IConnection connection) {
        authenticatedConnections.remove(connection);
    }


    @Override
    public IConnection getUserConnection(IUser user) {
        Iterator<Map.Entry<IConnection, IUser>> iterator = authenticatedConnections.entrySet().iterator();
        Map.Entry<IConnection, IUser> entry = null;
        while (iterator.hasNext()) {
            if ((entry = iterator.next()).getValue() == user) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public IUser getUserForConnection(IConnection connection) {
        return authenticatedConnections.get(connection);
    }

    public void start() {
        init();
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
            if (!socket.isClosed()) {
                try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        while (true) {
            try {
                Socket client = socket.accept();
                AbstractConnection con = connectionFactory.getConnection(this, client);
                if (con != null) {
                    con.start();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


//
}

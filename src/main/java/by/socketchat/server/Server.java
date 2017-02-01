package by.socketchat.server;

import by.socketchat.connection.AbstractConnectionFactory;
import by.socketchat.connection.Connection;
import by.socketchat.entity.message.AbstractMessageBuilder;
import by.socketchat.entity.user.User;
import by.socketchat.service.authentication.IAuthService;
import by.socketchat.service.chat.IChatService;
import by.socketchat.service.contacts.IContactsService;
import by.socketchat.service.registration.IRegistrationService;
import by.socketchat.session.ISession;
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
    private final String corruptedMessageResponse = "0";

    //FACTORIES
    private AbstractMessageBuilder messageBuilder;


    //SERVICES
    private AbstractConnectionFactory connectionFactory;
    private IRegistrationService registrationService;
    private IAuthService authenticationService;
    private IContactsService contactsService;
    private IChatService chatService;

    //Active sessions
    private Set<ISession> sessions;

    public Server() {
        sessions = new HashSet<ISession>();
    }


    @Autowired
    public void setMessageBuilder(AbstractMessageBuilder messageBuilder) {
        this.messageBuilder = messageBuilder;
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


    @Override
    public synchronized void closeConnection(Connection con) {
        closeSession(findSession(con));
        if (con.isAlive()) {
            con.stop();
        }
    }

    @Override
    public void onMessage(Connection connection, byte[] message) {
        Properties properties = Json.parse(Decoder.decode(message));
        if (properties.isEmpty()) {
            try {
                connection.write(corruptedMessageResponse.getBytes());//Message corrupted
            } catch (IOException e) {
                e.printStackTrace();
            }
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
                    chatService.send(messageBuilder.buildChatMessage(session.getUser(), properties));
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
                if (logOut(session)) {
                    closeConnection(session.getConnection());
                }
            default:
                try {
                    connection.write(corruptedMessageResponse.getBytes());//Message corrupted
                } catch (IOException e) {
                    e.printStackTrace();
                }

        }


    }

    private boolean logOut(ISession session) {
        closeConnection(session.getConnection());
        return true;//TODO
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
    public ISession findSession(Connection connection) {
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
                Connection con = connectionFactory.getConnection(this, client);
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

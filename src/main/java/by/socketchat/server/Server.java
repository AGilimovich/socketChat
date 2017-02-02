package by.socketchat.server;

import by.socketchat.connection.AbstractConnectionFactory;
import by.socketchat.connection.Connection;
import by.socketchat.entity.user.User;
import by.socketchat.protocol.ErrorType;
import by.socketchat.protocol.IMessageFormatter;
import by.socketchat.protocol.IMessageParser;
import by.socketchat.request.IRequest;
import by.socketchat.request.builder.IRequestBuilder;
import by.socketchat.service.authentication.IAuthService;
import by.socketchat.service.chat.IChatService;
import by.socketchat.service.contacts.IContactsService;
import by.socketchat.service.registration.IRegistrationService;
import by.socketchat.session.ISession;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Администратор on 29.11.2016.
 */

public class Server implements IServer {
    private final int DEFAULT_PORT = 8080;

    //FACTORIES
    private AbstractConnectionFactory connectionFactory;
    private IRequestBuilder requestBuilder;

    //Message Parser
    private IMessageParser messageParser;
    //Message Formatter
    private IMessageFormatter messageFormatter;

    //SERVICES
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
    public void setConnectionFactory(AbstractConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Autowired
    public void setRequestBuilder(IRequestBuilder requestBulder) {
        this.requestBuilder = requestBulder;
    }

    @Autowired
    public void setMessageParser(IMessageParser messageParser) {
        this.messageParser = messageParser;
    }

    @Autowired
    public void setMessageFormatter(IMessageFormatter messageFormatter) {
        this.messageFormatter = messageFormatter;
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
        ISession session = findSession(connection);
        IRequest request = requestBuilder.buildRequest(connection, messageParser.parse(message));

        if (request == null) {
            try {
                connection.write(messageFormatter.format(ErrorType.CORRUPTED_MESSAGE).getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                return;
            }

        }

        switch (request.getType()) {
            case AUTH:
                if (session != null) {
                    try {
                        connection.write(messageFormatter.format(ErrorType.ALREADY_AUTHENTICATED).getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        return;
                    }
                }
                if ((session = authenticationService.authenticate(request)) != null) {
                    sessions.add(session);
                    contactsService.updateAllAuthenticatedUsersContacts();
                }
                break;
            case CHAT:
                chatService.send(request);
                break;
            case CONTACTS:
                contactsService.updateUserContacts(request);
                break;
            case LOG_OUT:
                logOut(session);
                break;
            case REGISTRATION:
                registrationService.register(request);
                break;
            default:
                try {
                    connection.write(messageFormatter.format(ErrorType.CORRUPTED_MESSAGE).getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }


    }

    private boolean logOut(ISession session) {
        closeConnection((Connection) session.getConnection());
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

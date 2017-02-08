package by.socketchat.server;

import by.socketchat.connection.AbstractConnectionFactory;
import by.socketchat.connection.Connection;
import by.socketchat.entity.session.Session;
import by.socketchat.entity.user.User;
import by.socketchat.protocol.ErrorType;
import by.socketchat.protocol.IMessageFormatter;
import by.socketchat.protocol.IMessageParser;
import by.socketchat.repository.session.SessionRepository;
import by.socketchat.request.IRequest;
import by.socketchat.request.builder.IRequestBuilder;
import by.socketchat.service.authentication.IAuthService;
import by.socketchat.service.chat.IChatService;
import by.socketchat.service.contacts.IContactsService;
import by.socketchat.service.registration.IRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

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
    private Set<Session> sessions;
    private SessionRepository sessionRepository;

    public Server() {
        sessions = Collections.synchronizedSet(new HashSet<Session>());
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

    @Autowired
    public void setSessionRepository(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public synchronized void closeConnection(Connection con) {
        closeSession(findSession(con));
        if (con.isAlive()) {
            con.stop();
        }
    }

    @Override
    public void handleMessage(Connection connection, byte[] message) {
        IRequest request = requestBuilder.buildRequest(connection, messageParser.parse(message));
        if (request == null) {
            try {
                connection.write(messageFormatter.format(ErrorType.INVALID_MESSAGE_FORMAT).getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                return;
            }

        }
        Session session = findSession(connection);
        switch (request.getType()) {
            case COOKIES_AUTH:
            case AUTH:
                if (session != null) {
                    try {
                        connection.write(messageFormatter.format(ErrorType.INTERNAL_ERROR).getBytes());
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
            case LOGOUT:
                logOut(session);
                break;
            case REGISTRATION:
                registrationService.register(request);
                break;
            default:
                try {
                    connection.write(messageFormatter.format(ErrorType.INVALID_MESSAGE_FORMAT).getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }


    }

    private boolean logOut(Session session) {
        closeSession(session);
        return true;
    }

    @Override
    public Set<Session> getSessions() {
        return sessions;
    }


    @Override
    public Set<Session> findSession(User user) {
        Set<Session> ss = new HashSet<Session>();
        Iterator<Session> it = sessions.iterator();
        Session session = null;
        while (it.hasNext()) {
            if ((session = it.next()).getUser().equals(user)) {
                ss.add(session);
            }
        }
        return ss;
    }

    @Override
    public Session findSession(Connection connection) {
        Session session = null;
        Iterator<Session> it = sessions.iterator();
        while (it.hasNext()) {
            if ((session = it.next()).getConnection() == connection) {
                return session;
            }

        }
        return null;
    }

    @Override
    public Session findSession(UUID uuid) {
        Session session = null;
        Iterator<Session> it = sessions.iterator();
        while (it.hasNext()) {
            if ((session = it.next()).getUuid().equals(uuid)) {
                return session;
            }

        }
        return null;
    }


    @Override
    public Set<User> getAuthenticatedUsersSet() {
        Set<User> users = new HashSet<User>();
        Iterator<Session> it = sessions.iterator();
        while (it.hasNext()) {
            users.add(it.next().getUser());
        }
        return users;
    }

    private void closeSession(Session session) {
        if (session.isStoreSession()) {
            sessionRepository.save(session);
        }
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

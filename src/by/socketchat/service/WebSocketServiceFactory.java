package by.socketchat.service;

import by.socketchat.dao.AbstractDao;
import by.socketchat.server.IServer;
import by.socketchat.entity.user.IUser;
import by.socketchat.formatter.auth.AbstractAuthFormatter;
import by.socketchat.formatter.chat.AbstractChatFormatter;
import by.socketchat.formatter.registration.AbstractRegFormatter;
import by.socketchat.formatter.contacts.AbstractContactsFormatter;
import by.socketchat.service.authentication.AuthService;
import by.socketchat.service.authentication.IAuthService;
import by.socketchat.service.chat.ChatService;
import by.socketchat.service.chat.IChatService;
import by.socketchat.service.contacts.ContactsService;
import by.socketchat.service.contacts.IContactsService;
import by.socketchat.service.registration.IRegistrationService;
import by.socketchat.service.registration.RegistrationService;
import by.socketchat.service.validation.CredentialsValidationService;
import by.socketchat.service.validation.IValidationService;

/**
 * Created by Администратор on 26.12.2016.
 */
public class WebSocketServiceFactory extends AbstractServiceFactory {
    private IServer server;
    private AbstractRegFormatter regFormatter;
    private AbstractAuthFormatter authFormatter;
    private AbstractChatFormatter chatFormatter;
    private AbstractContactsFormatter contactsFormatter;
    private IValidationService validationService;


    private AbstractDao<IUser> userDao;


//    @Override
//    public IConnector getConnector() {
//        return new WebsocketConnector();
//    }

    @Override
    public IRegistrationService getRegService() throws ServiceInitException {
        if (server == null || userDao == null || regFormatter == null) {
            throw new ServiceInitException("Service not initialized");
        }

        return new RegistrationService(server, userDao, regFormatter, getValidationService());
    }


    @Override
    public IAuthService getAuthService() throws ServiceInitException {
        if (server == null || userDao == null || authFormatter == null) {
            throw new ServiceInitException("Service not initialized");
        }
        return new AuthService(userDao, authFormatter, server);
    }

    @Override
    public IChatService getChatService() throws ServiceInitException {
        if (server == null || userDao == null || chatFormatter == null) {
            throw new ServiceInitException("Service not initialized");
        }

        return new ChatService(server, userDao, chatFormatter);
    }

    @Override
    public IContactsService getContactsService() throws ServiceInitException {
        if (server == null || userDao == null || contactsFormatter == null) {
            throw new ServiceInitException("Service not initialized");
        }

        return new ContactsService(server, contactsFormatter, userDao);
    }

    @Override
    public IValidationService getValidationService() {
        if (validationService == null) {
            return new CredentialsValidationService();
        } else
            return validationService;


    }

    public void setServer(IServer server) {
        this.server = server;
    }

    @Override
    public void setRegFormatter(AbstractRegFormatter formatter) {
        this.regFormatter = formatter;
    }

    @Override
    public void setAuthFormatter(AbstractAuthFormatter formatter) {
        this.authFormatter = formatter;
    }

    @Override
    public void setChatFormatter(AbstractChatFormatter formatter) {
        this.chatFormatter = formatter;
    }

    @Override
    public void setContactsFormatter(AbstractContactsFormatter formatter) {
        this.contactsFormatter = formatter;
    }

    @Override
    public void setUserDao(AbstractDao<IUser> userDao) {
        this.userDao = userDao;
    }

}

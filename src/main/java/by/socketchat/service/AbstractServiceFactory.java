package by.socketchat.service;

import by.socketchat.dao.AbstractDao;
import by.socketchat.server.IServer;
import by.socketchat.entity.user.IUser;
import by.socketchat.formatter.auth.AbstractAuthFormatter;
import by.socketchat.formatter.chat.AbstractChatFormatter;
import by.socketchat.formatter.registration.AbstractRegFormatter;
import by.socketchat.formatter.contacts.AbstractContactsFormatter;
import by.socketchat.service.authentication.IAuthService;
import by.socketchat.service.chat.IChatService;
import by.socketchat.service.contacts.IContactsService;
import by.socketchat.service.registration.IRegistrationService;
import by.socketchat.service.validation.IValidationService;

/**
 * Created by Администратор on 26.12.2016.
 */
public abstract class AbstractServiceFactory {

    public abstract IRegistrationService getRegService() throws ServiceInitException;

    public abstract IAuthService getAuthService() throws ServiceInitException;

    public abstract IChatService getChatService() throws ServiceInitException;

    public abstract IContactsService getContactsService() throws ServiceInitException;

    public abstract IValidationService getValidationService();


    public abstract void setServer(IServer server);

    public abstract void setRegFormatter(AbstractRegFormatter formatter);

    public abstract void setAuthFormatter(AbstractAuthFormatter formatter);

    public abstract void setChatFormatter(AbstractChatFormatter formatter);

    public abstract void setContactsFormatter(AbstractContactsFormatter formatter);

    public abstract void setUserDao(AbstractDao<IUser> userDao);


//


}

package by.socketchat.service;

import by.socketchat.service.authentication.IAuthService;
import by.socketchat.service.chat.IChatService;
import by.socketchat.service.contacts.IContactsService;
import by.socketchat.service.registration.IRegistrationService;
import by.socketchat.service.validation.IValidationService;

/**
 * Created by Администратор on 26.12.2016.
 */
public abstract class AbstractServiceFactory {

    public abstract IRegistrationService getRegService();

    public abstract IAuthService getAuthService();

    public abstract IChatService getChatService();

    public abstract IContactsService getContactsService();

    public abstract IValidationService getValidationService();



}

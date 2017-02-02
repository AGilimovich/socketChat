package by.socketchat.protocol;

import by.socketchat.entity.message.ChatMessage;
import by.socketchat.entity.user.User;
import by.socketchat.service.authentication.AuthStatus;
import by.socketchat.service.registration.RegistrationStatus;

import javax.jws.soap.SOAPBinding;
import java.util.Collection;

/**
 * Created by Aleksandr on 01.02.2017.
 */
public interface IMessageFormatter {
    String format(RegistrationStatus status, User user);

    String format(Collection<User> users);

    String format(ChatMessage message);

    String format(AuthStatus status, User user);

    String format(ErrorType type);
}

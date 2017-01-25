package by.socketchat.formatter.contacts;

import by.socketchat.entity.user.User;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by Администратор on 28.12.2016.
 */
public abstract class AbstractContactsFormatter {
    public abstract String format(Collection<User> users);

}

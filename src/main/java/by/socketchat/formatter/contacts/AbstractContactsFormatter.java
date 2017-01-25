package by.socketchat.formatter.contacts;

import by.socketchat.entity.user.IUser;

import java.util.Collection;

/**
 * Created by Администратор on 28.12.2016.
 */
public abstract class AbstractContactsFormatter {
    public abstract String format(Collection<IUser> users);

}

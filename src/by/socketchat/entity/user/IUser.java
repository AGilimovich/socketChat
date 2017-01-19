package by.socketchat.entity.user;

import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Created by Администратор on 29.11.2016.
 */
public interface IUser {

    String getLogin();

    String getPassword();

    void setLogin(String login);

    void setPassword(String password);

    Date getRegistrationTime();

    List<IUser> getContacts();

    void addContact(IUser contact);

    long getId();

    Properties toProperties();


}

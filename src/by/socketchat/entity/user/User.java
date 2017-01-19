package by.socketchat.entity.user;

import by.socketchat.utility.idgenerator.IDGenerator;

import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Created by Администратор on 29.11.2016.
 */
public class User implements IUser {
    private final long id;
    private String name; //TODO
    private String login;
    private String password;
    private final Date registrationTime;
    private List<IUser> contactList;//TODO

    private User(String login, String password) {
        id = IDGenerator.generateUserID();
        registrationTime = new Date();
        this.login = login;
        this.password = password;

    }

    public static IUser newUser(String login, String password) {
        return new User(login, password);
    }

    public String getLogin() {
        return login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Date getRegistrationTime() {
        return registrationTime;
    }

    @Override
    public List<IUser> getContacts() {
        return contactList;
    }

    @Override
    public void addContact(IUser contact) {
        contactList.add(contact);
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public Properties toProperties() {
        return new Properties() {{
            setProperty("id", String.valueOf(id));
            setProperty("login", login);

        }};
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (this == o) return true;
        User temp = null;
        if (o instanceof User) {
            temp = (User) o;
        } else return false;
        if (id == temp.getId() && login.equals(temp.getLogin()) && password.equals(temp.getPassword()) && registrationTime.equals(temp.getRegistrationTime())) {
            return true;
        }
        else return false;

    }
}

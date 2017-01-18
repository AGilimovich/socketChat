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
    private String name;
    private String password;
    private final Date registrationTime;
    private List<IUser> contactList;

    private User(String name, String password) {
        id = IDGenerator.generateUserID();
        registrationTime = new Date();
        this.name = name;
        this.password = password;

    }

    public static IUser newUser(String name, String password) {
        return new User(name, password);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setName(String name) {
        this.name = name;
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
            setProperty("name", name);

        }};
    }
}

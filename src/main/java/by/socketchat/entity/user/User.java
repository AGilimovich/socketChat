package by.socketchat.entity.user;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Created by Администратор on 29.11.2016.
 */

@Entity
@Table(name = "users")

@NamedQueries({
        @NamedQuery(name = "User.getAll", query = "select distinct u from User u"),
        @NamedQuery(name = "User.findById", query = "select distinct u from User u where u.id = :id"),
        @NamedQuery(name = "User.findByLogin", query = "select distinct u from User u where u.login = :login")
})

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name; //TODO
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    @Generated(GenerationTime.ALWAYS)
    @Column(name = "regTime", insertable=false,updatable=false)
    private Date registrationTime;

    @OneToMany
    @JoinColumn(name = "contacts")
    private List<User> contactList = new ArrayList<User>();


    public User(Long id, String name, String login, String password, Date registrationTime) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.registrationTime = registrationTime;
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Date getRegistrationTime() {
        return registrationTime;
    }

    public String getName() {
        return name;
    }

    public List<User> getContacts() {
        return contactList;
    }

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
        } else return false;

    }


}

package by.socketchat.entity.message;

import by.socketchat.protocol.MessageType;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Aleksandr on 04.01.2017.
 */
@Entity
@Table(name = "regMessages")
@NamedQueries({
        @NamedQuery(name = "RegMessage.getAll", query = "select distinct m from RegMessage m"),
        @NamedQuery(name = "RegMessage.findById", query = "select distinct m from RegMessage m where m.id = :id")
})
public class RegMessage implements IMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    @Column(name = "sendTime")
    private Date time;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;


    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "address")
    private String address;
    @Column(name = "phone")
    private String phone;
    @Column(name = "birthday")
    private Date birthday;

    @Transient
    private MessageType type;


    public RegMessage(Long id, Date time, String login, String password, String name, String email, String address, String phone, Date birthday) {
        type = MessageType.REGISTRATION;
        this.id = id;
        this.time = time;
        this.login = login;
        this.password = password;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.birthday = birthday;
    }

    public RegMessage() {
        this.type = MessageType.REGISTRATION;
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

    public Date getTime() {
        return time;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public Date getBirthday() {
        return birthday;
    }

    @Override
    public MessageType getType() {
        return type;
    }


}

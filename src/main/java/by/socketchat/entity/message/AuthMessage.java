package by.socketchat.entity.message;


import by.socketchat.protocol.MessageType;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "authMessages")
@NamedQueries({
        @NamedQuery(name = "AuthMessage.getAll", query = "select distinct m from AuthMessage m"),
        @NamedQuery(name = "AuthMessage.findById", query = "select distinct m from AuthMessage m where m.id = :id")
})
public class AuthMessage implements IMessage {

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
    @Column(name = "rememberMe")
    private boolean rememberMe;
    @Transient
    private MessageType type;


    public AuthMessage(Long id, Date time, String login, String password, boolean rememberMe) {
        type = MessageType.AUTH;
        this.id = id;
        this.time = time;
        this.login = login;
        this.password = password;
        this.rememberMe = rememberMe;
    }

    public AuthMessage() {
        this.type = MessageType.AUTH;
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


    @Override
    public MessageType getType() {
        return type;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }
}

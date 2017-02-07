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
    @Transient
    private MessageType type;


    public RegMessage(Long id, Date time, String login, String password) {
        type = MessageType.REGISTRATION;
        this.id = id;
        this.time = time;
        this.login = login;
        this.password = password;
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


    @Override
    public MessageType getType() {
        return type;
    }
}

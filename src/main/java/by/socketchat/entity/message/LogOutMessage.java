package by.socketchat.entity.message;

import by.socketchat.entity.user.User;
import by.socketchat.protocol.MessageType;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "logoutMessages")
@NamedQueries({
        @NamedQuery(name = "LogoutMessage.getAll", query = "select distinct m from LogoutMessage m"),
        @NamedQuery(name = "LogoutMessage.findById", query = "select distinct m from LogoutMessage m where m.id = :id")
})

public class LogoutMessage implements IMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    @Column(name = "sendTime")
    private Date time;
    @OneToOne
    @JoinColumn(name = "user")
    private User user;
    @Transient
    private MessageType type;


    public LogoutMessage(Long id, Date time, User user) {
        type = MessageType.LOGOUT;
        this.id = id;
        this.time = time;
        this.user = user;
    }

    public LogoutMessage() {
        this.type = MessageType.LOGOUT;
    }


    public User getUser() {
        return user;
    }

    public long getId() {
        return 0;
    }

    public Date getTime() {
        return null;
    }

    @Override
    public MessageType getType() {
        return type;
    }
}

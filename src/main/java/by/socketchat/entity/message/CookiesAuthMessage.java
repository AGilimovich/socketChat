package by.socketchat.entity.message;

import by.socketchat.protocol.MessageType;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Aleksandr on 08.02.2017.
 */

@Entity
@Table(name = "cookiesAuthMessages")
@NamedQueries({
        @NamedQuery(name = "CookiesAuthMessage.getAll", query = "select distinct m from CookiesAuthMessage m"),
        @NamedQuery(name = "CookiesAuthMessage.findById", query = "select distinct m from CookiesAuthMessage m where m.id = :id")
})
public class CookiesAuthMessage implements IMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    @Column(name = "sendTime")
    private Date time;
    @Column(name = "login")
    private String login;
    @Column(name = "uuid")
    private UUID uuid;
    @Transient
    private MessageType type;

    public CookiesAuthMessage(Long id, Date time, String login, UUID uuid) {
        type = MessageType.COOKIES_AUTH;
        this.id = id;
        this.time = time;
        this.login = login;
        this.uuid = uuid;
    }

    public CookiesAuthMessage() {
        this.type = MessageType.AUTH;
    }


    @Override
    public MessageType getType() {
        return type;
    }

    public Long getId() {
        return id;
    }

    public Date getTime() {
        return time;
    }

    public String getLogin() {
        return login;
    }

    public UUID getUuid() {
        return uuid;
    }
}

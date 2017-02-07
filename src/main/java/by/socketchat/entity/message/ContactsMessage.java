package by.socketchat.entity.message;

import by.socketchat.entity.message.IMessage;
import by.socketchat.entity.user.User;
import by.socketchat.protocol.MessageType;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "contactsMessages")
@NamedQueries({
        @NamedQuery(name = "ContactsMessage.getAll", query = "select distinct m from ContactsMessage m"),
        @NamedQuery(name = "ContactsMessage.findById", query = "select distinct m from ContactsMessage m where m.id = :id")
})

public class ContactsMessage implements IMessage {
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


    public ContactsMessage(Long id, Date time, User user) {
        type = MessageType.CONTACTS;
        this.id = id;
        this.time = time;
        this.user = user;
    }

    public ContactsMessage() {
        this.type = MessageType.CONTACTS;
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

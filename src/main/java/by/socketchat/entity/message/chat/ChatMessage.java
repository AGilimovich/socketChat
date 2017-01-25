package by.socketchat.entity.message.chat;

import by.socketchat.entity.user.User;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Администратор on 27.12.2016.
 */

@Entity
@Table(name = "messages")

@NamedQueries({
        @NamedQuery(name = "ChatMessage.getAll", query = "select distinct m from ChatMessage m"),
        @NamedQuery(name = "ChatMessage.findById", query = "select distinct m from ChatMessage m where m.id = :id")
})

public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver")
    private User receiver;
    @Column(name = "content")
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Generated(GenerationTime.ALWAYS)
    @Column(name = "sendTime", insertable = false, updatable = false)

    private Date sendTime;

    public ChatMessage(Long id, User sender, User receiver, String content, Date sendTime) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.sendTime = sendTime;
    }

    public ChatMessage() {

    }


    public long getId() {
        return id;
    }


    public User getSender() {
        return sender;
    }


    public User getReceiver() {
        return receiver;
    }

    public String getContent() {
        return content;
    }


    public Date getSendTime() {
        return sendTime;
    }


}

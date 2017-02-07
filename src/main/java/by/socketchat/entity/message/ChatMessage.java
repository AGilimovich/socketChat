package by.socketchat.entity.message;

import by.socketchat.entity.message.IMessage;
import by.socketchat.entity.user.User;
import by.socketchat.protocol.MessageType;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "messages")

@NamedQueries({
        @NamedQuery(name = "ChatMessage.getAll", query = "select distinct m from ChatMessage m"),
        @NamedQuery(name = "ChatMessage.findById", query = "select distinct m from ChatMessage m where m.id = :id")
})

public class ChatMessage implements IMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "sender")
    private User sender;

    @OneToOne
    @JoinColumn(name = "receiver")
    private User receiver;
    @Column(name = "content")
    private String content;

    @CreationTimestamp
    @Column(name = "sendTime")
    private Date sendTime;

    @Transient
    private MessageType type;

    public ChatMessage(Long id, User sender, User receiver, String content, Date sendTime) {
        this.id = id;
        type = MessageType.CHAT;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.sendTime = sendTime;
    }

    public ChatMessage() {
        type = MessageType.CHAT;
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


    @Override
    public MessageType getType() {
        return type;
    }
}

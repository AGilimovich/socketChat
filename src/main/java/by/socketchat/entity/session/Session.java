package by.socketchat.entity.session;

import by.socketchat.connection.Connection;
import by.socketchat.connection.IConnection;
import by.socketchat.entity.user.User;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Aleksandr on 01.02.2017.
 */
@Entity
@Table(name = "session")
@NamedQueries({
        @NamedQuery(name = "Session.getAll", query = "select distinct s from Session s"),
        @NamedQuery(name = "Session.findByUuid", query = "select distinct s from Session s where s.uuid = :uuid"),


})
public class Session {
    @Id
    @Column(name = "uuid")
    private UUID uuid;
    @OneToOne
    @JoinColumn(name = "user")
    private User user;
    @Column(name = "startingTime")
    private Date startingTime;
    @Column(name = "expiringTime")
    private Date expiringTime;

    @Transient
    private IConnection connection;
    @Transient
    private boolean storeSession;


    public Session() {
    }

    public Session(UUID uuid, User user, Date startingTime, Date expiringTime) {
        this.uuid = uuid;
        this.user = user;
        this.startingTime = startingTime;
        this.expiringTime = expiringTime;


    }

    public Session(UUID uuid, User user, IConnection connection, boolean storeSession) {
        this.uuid = uuid;
        this.user = user;
        this.connection = connection;
        this.storeSession = storeSession;
        startingTime = new Date();
    }


    public User getUser() {
        return user;
    }


    public IConnection getConnection() {
        return connection;
    }

    public void setConnection(IConnection connection) {
        this.connection = connection;
    }

    public Date getStartingTime() {
        return startingTime;
    }


    public UUID getUuid() {
        return uuid;
    }

    public Date getExpiringTime() {
        return expiringTime;
    }

    public boolean isStoreSession() {
        return storeSession;
    }
}

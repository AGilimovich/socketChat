package by.socketchat.repository.message;

import by.socketchat.entity.message.ChatMessage;
import by.socketchat.entity.message.ContactsMessage;
import by.socketchat.entity.message.LogoutMessage;
import by.socketchat.repository.AbstractRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Aleksandr on 07.02.2017.
 */
@Repository
public class LogoutMessageRepository extends AbstractRepository<LogoutMessage> {

    private SessionFactory sessionFactory;

    @Autowired
    public LogoutMessageRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }


    @Override
    public void delete(LogoutMessage ob) {

    }

    @Transactional
    @Override
    public LogoutMessage save(LogoutMessage message) {
        Serializable id = currentSession().save(message);
        return new LogoutMessage((Long) id, message.getTime(), message.getUser());
    }

    @Transactional
    @Override
    public List<LogoutMessage> getAll() {
        return currentSession().
                getNamedQuery("Logout.getAll").list();
    }

    @Transactional
    @Override
    public LogoutMessage findById(long id) {
        return (LogoutMessage) currentSession().
                getNamedQuery("LogoutMessage.findById").setParameter("id", id).uniqueResult();
    }

    @Transactional
    @Override
    public LogoutMessage findByLogin(String login) {
        return null;
    }
}

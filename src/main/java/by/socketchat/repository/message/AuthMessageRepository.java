package by.socketchat.repository.message;

import by.socketchat.entity.message.AuthMessage;
import by.socketchat.entity.message.ChatMessage;
import by.socketchat.repository.AbstractRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Aleksandr on 06.02.2017.
 */

@Repository
public class AuthMessageRepository extends AbstractRepository<AuthMessage> {

    private SessionFactory sessionFactory;

    @Autowired
    public AuthMessageRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void delete(AuthMessage ob) {

    }

    @Transactional
    @Override
    public AuthMessage save(AuthMessage message) {
        Serializable id = currentSession().save(message);
        return new AuthMessage((Long) id, message.getTime(), message.getLogin(), message.getPassword());
    }

    @Transactional
    @Override
    public List<AuthMessage> getAll() {
        return currentSession().
                getNamedQuery("AuthMessage.getAll").list();
    }

    @Transactional
    @Override
    public AuthMessage findById(long id) {
        return (AuthMessage) currentSession().
                getNamedQuery("AuthMessage.findById").setParameter("id", id).uniqueResult();
    }

    @Transactional
    @Override
    public AuthMessage findByLogin(String login) {
        return null;
    }
}

package by.socketchat.repository.message;

import by.socketchat.entity.message.ChatMessage;
import by.socketchat.entity.message.RegMessage;
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
public class RegMessageRepository extends AbstractRepository<RegMessage> {


    private SessionFactory sessionFactory;

    @Autowired
    public RegMessageRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional
    @Override
    public void delete(RegMessage ob) {

    }

    @Transactional
    @Override
    public RegMessage save(RegMessage message) {
        Serializable id = currentSession().save(message);
        return new RegMessage((Long) id, message.getTime(), message.getLogin(), message.getPassword(), message.getName(), message.getEmail(), message.getAddress(), message.getPhone(), message.getBirthday());
    }

    @Transactional
    @Override
    public List<RegMessage> getAll() {
        return currentSession().
                getNamedQuery("RegMessage.getAll").list();
    }

    @Transactional
    @Override
    public RegMessage findById(long id) {
        return (RegMessage) currentSession().
                getNamedQuery("RegMessage.findById").setParameter("id", id).uniqueResult();
    }

    @Transactional
    @Override
    public RegMessage findByLogin(String login) {
        return null;
    }
}

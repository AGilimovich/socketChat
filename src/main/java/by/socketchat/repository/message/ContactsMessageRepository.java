package by.socketchat.repository.message;

import by.socketchat.entity.message.ChatMessage;
import by.socketchat.entity.message.ContactsMessage;
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
 * Created by Aleksandr on 07.02.2017.
 */

@Repository
public class ContactsMessageRepository extends AbstractRepository<ContactsMessage> {

    private SessionFactory sessionFactory;

    @Autowired
    public ContactsMessageRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }


    @Override
    public void delete(ContactsMessage ob) {

    }

    @Transactional
    @Override
    public ContactsMessage save(ContactsMessage message) {
        Serializable id = currentSession().save(message);
        return new ContactsMessage((Long) id, message.getTime(), message.getUser());
    }

    @Transactional
    @Override
    public List<ContactsMessage> getAll() {
        return currentSession().
                getNamedQuery("ContactsMessage.getAll").list();
    }

    @Transactional
    @Override
    public ContactsMessage findById(long id) {
        return (ContactsMessage) currentSession().
                getNamedQuery("ContactsMessage.findById").setParameter("id", id).uniqueResult();
    }

    @Transactional
    @Override
    public ContactsMessage findByLogin(String login) {
        return null;
    }
}

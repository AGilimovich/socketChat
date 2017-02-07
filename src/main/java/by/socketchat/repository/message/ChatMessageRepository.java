package by.socketchat.repository.message;

import by.socketchat.repository.AbstractRepository;
import by.socketchat.entity.message.ChatMessage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Администратор on 27.12.2016.
 */
@Repository
public class ChatMessageRepository extends AbstractRepository<ChatMessage> {
    private SessionFactory sessionFactory;

    @Autowired
    public ChatMessageRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional
    @Override
    public void delete(ChatMessage message) {
        //TODO
    }

    @Transactional
    @Override
    public ChatMessage save(ChatMessage message) {
        Serializable id = currentSession().save(message);
        return new ChatMessage((Long) id, message.getSender(), message.getReceiver(), message.getContent(), message.getSendTime());
    }

    @Transactional
    @Override
    public List<ChatMessage> getAll() {

        return currentSession().
                getNamedQuery("ChatMessage.getAll").list();

    }

    @Transactional
    @Override
    public ChatMessage findById(long id) {
        return (ChatMessage) currentSession().
                getNamedQuery("ChatMessage.findById").setParameter("id", id).uniqueResult();
    }

    @Transactional
    @Override
    public ChatMessage findByLogin(String name) {
        return null;
    }

    @Override
    public ChatMessage findByEmail(String email) {
        return null;
    }


}

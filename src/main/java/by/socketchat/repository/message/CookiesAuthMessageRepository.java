package by.socketchat.repository.message;

import by.socketchat.entity.message.CookiesAuthMessage;
import by.socketchat.repository.AbstractRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Repository
public class CookiesAuthMessageRepository extends AbstractRepository<CookiesAuthMessage> {

    private SessionFactory sessionFactory;

    @Autowired
    public CookiesAuthMessageRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void delete(CookiesAuthMessage ob) {

    }

    @Transactional
    @Override
    public CookiesAuthMessage save(CookiesAuthMessage message) {
        Serializable id = currentSession().save(message);
        return new CookiesAuthMessage((Long) id, message.getTime(), message.getLogin(), message.getUuid());
    }

    @Transactional
    @Override
    public List<CookiesAuthMessage> getAll() {
        return currentSession().
                getNamedQuery("CookiesAuthMessage.getAll").list();
    }

    @Transactional
    @Override
    public CookiesAuthMessage findById(long id) {
        return (CookiesAuthMessage) currentSession().
                getNamedQuery("CookiesAuthMessage.findById").setParameter("id", id).uniqueResult();
    }

    @Transactional
    @Override
    public CookiesAuthMessage findByLogin(String login) {
        return null;
    }

    @Transactional
    @Override
    public CookiesAuthMessage findByEmail(String email) {
        return null;
    }

    @Override
    public CookiesAuthMessage findByUuid(UUID uuid) {
        return null;
    }
}

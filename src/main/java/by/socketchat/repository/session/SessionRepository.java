package by.socketchat.repository.session;

import by.socketchat.entity.session.Session;
import by.socketchat.repository.AbstractRepository;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@Repository
public class SessionRepository extends AbstractRepository<Session> {
    private SessionFactory sessionFactory;

    @Inject
    public SessionRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private org.hibernate.Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional
    @Override
    public void delete(Session ob) {

    }

    @Transactional
    @Override
    public Session save(Session session) {
        currentSession().save(session);
        return session;
    }

    @Transactional
    @Override
    public List<Session> getAll() {
        return currentSession().
                getNamedQuery("Session.getAll").list();
    }

    @Transactional
    @Override
    public Session findById(long id) {
        return null;
    }

    @Transactional
    @Override
    public Session findByLogin(String login) {
        return null;
    }

    @Transactional
    @Override
    public Session findByEmail(String email) {
        return null;
    }

    @Transactional
    @Override
    public Session findByUuid(UUID uuid) {
        return (Session) currentSession().
                getNamedQuery("User.findByUuid").setParameter("uuid", uuid).uniqueResult();
    }
}

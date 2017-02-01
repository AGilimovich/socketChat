package by.socketchat.repository.user;

import by.socketchat.repository.AbstractRepository;
import by.socketchat.entity.user.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Администратор on 27.12.2016.
 */
@Repository

public class UserRepository extends AbstractRepository<User> {

    private SessionFactory sessionFactory;

    @Inject
    public UserRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional
    @Override
    public void remove(User user) {

    }

    @Transactional
    @Override
    public User save(User user) {
        Serializable id = currentSession().save(user);
        return new User((Long) id, user.getName(), user.getLogin(), user.getPassword(), user.getRegistrationTime());
    }

    @Transactional
    @Override
    public User findById(long id) {
        return (User) currentSession().
                getNamedQuery("User.findById").setParameter("id", id).uniqueResult();
    }

    @Transactional
    @Override
    public List<User> getAll() {
        return currentSession().
                getNamedQuery("User.getAll").list();
    }

    @Transactional
    @Override
    public synchronized User findByLogin(String login) {

        return (User) currentSession().
                getNamedQuery("User.findByLogin").setParameter("login", login).uniqueResult();
    }


}

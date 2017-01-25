package by.socketchat.dao.user;

import by.socketchat.dao.AbstractRepository;
import by.socketchat.entity.user.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
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
        return (User) currentSession().get(User.class, id);
    }

    @Transactional
    @Override
    public List<User> getAll() {
        List<User> users = null;
        users = currentSession()
                .createCriteria(User.class).list();
        return users;
    }

    @Transactional
    @Override
    public synchronized User findByLogin(String login) {

        EntityManager em = new ;
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Pet> cq = cb.createQuery(Pet.class);
        Root<Pet> pet = cq.from(Pet.class);
        cq.select(pet);
        TypeQuery<Pet> q = em.createQuery(cq);
        List<Pet> allPets = q.getResultList();

        currentSession().
                Criter


        return (User) currentSession()
                .createCriteria(User.class)
                .add(Restrictions.eq("login", login))
                .list().get(0);
    }


}

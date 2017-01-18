package by.socketchat.dao.user;

import by.socketchat.dao.AbstractDao;
import by.socketchat.entity.user.IUser;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Администратор on 27.12.2016.
 */
public class UserDao extends AbstractDao<IUser> {
    private Set<IUser> users;

    public UserDao() {
        users = new HashSet<>();
    }

    @Override
    public void add(IUser user) {
        users.add(user);
    }

    @Override
    public void remove(IUser user) {
        users.remove(user);
    }

    @Override
    public void save(IUser ob) {

    }

    @Override
    public IUser findById(long id) {
        Iterator<IUser> it = users.iterator();
        IUser user = null;
        while (it.hasNext()) {
            user = it.next();
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    @Override
    public Set<IUser> getAll() {
        return users;
    }

    @Override
    public synchronized IUser findByName(String name) {
        IUser user = null;
        Iterator<IUser> it = users.iterator();
        while (it.hasNext()) {
            if ((user = it.next()).getName().equals(name)) {
                return user;
            }
        }
        return null;
    }


}

package by.socketchat.dao;

import java.util.Set;

/**
 * Created by Администратор on 27.12.2016.
 */
public abstract class AbstractDao<T> {
    public abstract void add(T ob);

    public abstract void remove(T ob);

    public abstract void save(T ob);

    public abstract Set<T> getAll();

    public abstract T findById(long id);

    public abstract T findByName(String name);

}

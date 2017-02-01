package by.socketchat.repository;

import java.util.List;

/**
 * Created by Администратор on 27.12.2016.
 */
public abstract class AbstractRepository<T> {

    public abstract void remove(T ob);

    public abstract T save(T ob);

    public abstract List<T> getAll();

    public abstract T findById(long id);

    public abstract T findByLogin(String name);

}

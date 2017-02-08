package by.socketchat.repository;

import java.util.List;
import java.util.UUID;


public abstract class AbstractRepository<T> {

    public abstract void delete(T ob);

    public abstract T save(T ob);

    public abstract List<T> getAll();

    public abstract T findById(long id);

    public abstract T findByLogin(String login);

    public abstract T findByEmail(String email);

    public abstract T findByUuid(UUID uuid);

}

package by.socketchat.entity.message.request.auth;

import by.socketchat.entity.user.IUser;

import java.util.Date;

/**
 * Created by Aleksandr on 04.01.2017.
 */
public abstract class AbstractAuthRequest {

    public abstract long getId();

    public abstract String getName();

    public abstract String getPassword();

    public abstract Date getTime();

}

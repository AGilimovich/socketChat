package by.socketchat.entity.message.request.contacts;

import by.socketchat.entity.user.User;

import java.util.Date;

/**
 * Created by Aleksandr on 05.01.2017.
 */
public abstract class AbstractContactsRequest {

    public abstract User getUser();


    public abstract long getId();

    public abstract Date getTime();

}

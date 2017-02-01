package by.socketchat.entity.message.request.contacts;

import by.socketchat.entity.user.User;

import java.util.Date;

/**
 * Created by Aleksandr on 05.01.2017.
 */
public class ContactsRequest extends AbstractContactsRequest {

    private long id;
    private Date time;
    private User user;


    public ContactsRequest(User user) {
        this.user = user;
        id = 0; //TODO
        time = new Date();


    }
    @Override
    public User getUser() {
        return user;
    }

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public Date getTime() {
        return null;
    }


}

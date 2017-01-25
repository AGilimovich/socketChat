package by.socketchat.entity.message.request.contacts;

import by.socketchat.entity.user.IUser;
import by.socketchat.utility.idgenerator.IDGenerator;

import java.util.Date;

/**
 * Created by Aleksandr on 05.01.2017.
 */
public class ContactsRequest extends AbstractContactsRequest {

    private long id;
    private Date time;
    private IUser user;


    public ContactsRequest(IUser user) {
        this.user = user;
        id = IDGenerator.generateMessageID();
        time = new Date();


    }
    @Override
    public IUser getUser() {
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

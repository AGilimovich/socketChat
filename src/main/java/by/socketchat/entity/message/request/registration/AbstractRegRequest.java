package by.socketchat.entity.message.request.registration;

import java.util.Date;

/**
 * Created by Aleksandr on 04.01.2017.
 */
public abstract class AbstractRegRequest {

    public abstract String getLogin();

    public abstract String getPassword();

    public abstract Date getTime();

    public abstract long getId();


}

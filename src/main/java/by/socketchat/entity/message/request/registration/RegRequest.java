package by.socketchat.entity.message.request.registration;

import java.util.Date;

/**
 * Created by Aleksandr on 04.01.2017.
 */
public class RegRequest extends AbstractRegRequest {


    private long id;
    private Date time;
    private String name;
    private String password;


    public RegRequest(String name, String password) {
        id = 0; // TODO
        time = new Date();
        this.name = name;
        this.password = password;
    }


    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getLogin() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Date getTime() {
        return time;
    }


}

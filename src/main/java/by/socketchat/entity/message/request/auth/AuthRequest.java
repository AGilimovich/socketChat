package by.socketchat.entity.message.request.auth;

import by.socketchat.utility.idgenerator.IDGenerator;

import java.util.Date;

/**
 * Created by Aleksandr on 04.01.2017.
 */
public class AuthRequest extends AbstractAuthRequest {
    private long id;
    private Date time;
    private String name;
    private String password;



    public AuthRequest(String name, String password) {
        id = IDGenerator.generateMessageID();
        time = new Date();
        this.name = name;
        this.password = password;
    }




    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getName() {
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

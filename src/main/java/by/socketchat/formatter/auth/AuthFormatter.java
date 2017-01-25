package by.socketchat.formatter.auth;

import by.socketchat.service.authentication.AuthStatus;
import by.socketchat.formatter.MessageType;
import by.socketchat.utility.json.Json;

import java.util.Properties;

/**
 * Created by Администратор on 27.12.2016.
 */
public class AuthFormatter extends AbstractAuthFormatter {


    @Override
    public String format(AuthStatus status) {
        Properties props = new Properties();
        props.setProperty("type", String.valueOf(MessageType.AUTH.getCode()));
        props.setProperty("status", String.valueOf(status.getCode()));
        return Json.stringify(props);
    }
}




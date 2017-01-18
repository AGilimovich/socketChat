package by.socketchat.formatter.registration;

import by.socketchat.formatter.MessageType;
import by.socketchat.utility.json.Json;
import by.socketchat.service.registration.RegistrationStatus;

import java.util.Properties;

/**
 * Created by Администратор on 27.12.2016.
 */
public class RegistrationFormatter extends AbstractRegFormatter {

    @Override
    public String format(RegistrationStatus status) {
        Properties props = new Properties();
        props.setProperty("type", String.valueOf(MessageType.REGISTRATION.getCode()));
        props.setProperty("status", String.valueOf(status.getCode()));
        return Json.stringify(props);
    }
}




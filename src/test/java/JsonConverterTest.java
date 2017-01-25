import by.socketchat.entity.user.IUser;
import by.socketchat.utility.json.Json;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by Администратор on 26.12.2016.
 */
public class JsonConverterTest {


    public void startUserCollectionStringifierTest(Collection<IUser> users) {
        System.out.println(Json.stringify(users));
    }

    public void startUserStringifierTest(IUser user) {
        System.out.println(Json.stringify(user));
    }

    public void startPropertiesStringifierTest(Properties props) {
        System.out.println(Json.stringify(props));
    }

    public void startCollectionStringifierTest(Collection collection) {
        System.out.println(Json.stringify(collection));
    }

    public void startParseTest(String text) {
        Properties props = Json.parse(text);
        Enumeration e = props.propertyNames();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            System.out.println(props.getProperty(key));
        }

    }


}

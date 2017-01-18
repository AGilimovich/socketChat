import by.socketchat.entity.user.IUser;
import by.socketchat.entity.user.User;
import by.socketchat.utility.json.Json;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

/**
 * Created by Администратор on 26.12.2016.
 */
public class Main {

    public static void main(String[] args) {


        String JSON_STRING = "{\"key4\":4,\"key3\":\"value3\",\"key2\":\"value2\",\"key1\":\"value1\",\"contacts\":[{\"user0\":{\"name\":1,\"id\":0},{\"user1\":{\"name\":2,\"id\":1},{\"user2\":{\"name\":3,\"id\":2}]}";


        Properties props = new Properties();


        IUser user1 = User.newUser("1", "1");
        IUser user2 = User.newUser("2", "2");
        IUser user3 = User.newUser("3", "3");


        Collection<IUser> collection = new ArrayList<IUser>();
        collection.add(user1);
        collection.add(user2);
        collection.add(user3);

        props.put("key1", "value1");
        props.put("key2", "value2");
        props.put("key3", "value3");
        props.put("key4", "value4");
        props.put("contacts", Json.stringify(collection));

        JsonConverterTest test = new JsonConverterTest();
        test.startCollectionStringifierTest(collection);
        test.startPropertiesStringifierTest(props);
        test.startParseTest(JSON_STRING);


//        EncoderTest test = new EncoderTest();
//        String text = null;
//        try {
//            text = new String(new byte[] {-127,-127,-7,80,17,-50,-56}, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        test.startTest(text);

//        byte[] a = {0b0111_1111};
//        try {
//            System.out.println((new String(a, "UTF-8")));
//            System.out.println("adasd");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

    }
}

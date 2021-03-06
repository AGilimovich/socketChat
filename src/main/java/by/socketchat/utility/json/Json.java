package by.socketchat.utility.json;

import by.socketchat.entity.user.User;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Json {

    protected static final String QUOTE = "\"";
    protected static final String COLON = ":";
    protected static final String COMMA = ",";
    protected static final String SQUARE_BRACKET_OPENED = "[";
    protected static final String SQUARE_BRACKET_CLOSED = "]";
    protected static final String FIGURED_BRACKET_OPENED = "{";
    protected static final String FIGURED_BRACKET_CLOSED = "}";

    private static final String arrayRegex = "\\[.*\\]";
    private static final String objectRegex = "\\{.*\\}";
    private static final String numberRegex = "\\d+";
    private static final String propertyRegex = "\"(\\w+)\":\"*([\\w@\\.]+|\\[.+\\]|\\{.+\\})\"*";

    private static final Pattern arrayPattern = Pattern.compile(arrayRegex);
    private static final Pattern objectPattern = Pattern.compile(objectRegex);
    private static final Pattern numberPattern = Pattern.compile(numberRegex);
    private static final Pattern propertyPattern = Pattern.compile(propertyRegex);


    public static String stringify(Properties message) {

        StringBuilder sb = new StringBuilder();
        Enumeration enumeration = message.propertyNames();
        boolean isEmpty = true;
        while (enumeration.hasMoreElements()) {
            if (!isEmpty) {
                sb.append(COMMA);
            } else {
                sb.append(FIGURED_BRACKET_OPENED);
            }
            String key = (String) enumeration.nextElement();
            sb.append(QUOTE + key + QUOTE + COLON);
            Matcher matcherArray = arrayPattern.matcher(message.getProperty(key));
            Matcher matcherObject = objectPattern.matcher(message.getProperty(key));
            Matcher matcherNumber = numberPattern.matcher(message.getProperty(key));
            if (matcherArray.matches() || matcherObject.matches() || matcherNumber.matches()) {
                sb.append(message.getProperty(key));
            } else sb.append(QUOTE + message.getProperty(key) + QUOTE);
            isEmpty = false;
        }
        sb.append(FIGURED_BRACKET_CLOSED);
        return sb.toString();
    }


    public static <T extends User> String stringify(T user) {
        Properties properties = new Properties();
        properties.setProperty("id", String.valueOf(user.getId()));
        properties.setProperty("login", user.getLogin());
        properties.setProperty("name", user.getName());
        return stringify(properties);
    }


    public static String stringify(Collection<User> users) {
        StringBuilder sb = new StringBuilder();
        Iterator<User> it = users.iterator();
        boolean isEmpty = true;
        sb.append(SQUARE_BRACKET_OPENED);
        while (it.hasNext()) {
            if (!isEmpty) {
                sb.append(COMMA);
            }
            User user = it.next();
            sb.append(stringify(user));
            isEmpty = false;
        }
        sb.append(SQUARE_BRACKET_CLOSED);
        return sb.toString();

    }


    public static Properties parse(String jsonString) throws IllegalArgumentException {

        Matcher matcherProperty = propertyPattern.matcher(jsonString);

        Properties props = new Properties();


        while (matcherProperty.find()) {
            props.put(matcherProperty.group(1), matcherProperty.group(2));
        }
        return props;
    }



}

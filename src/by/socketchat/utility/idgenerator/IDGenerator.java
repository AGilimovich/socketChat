package by.socketchat.utility.idgenerator;

/**
 * Created by Администратор on 27.12.2016.
 */
public class IDGenerator {
    private static volatile long userID = 0;
    private static volatile long messageID = 0;
    private static volatile long eventID = 0;


    public synchronized static long generateUserID() {
        return userID++;
    }

    public synchronized static long generateMessageID() {
        return messageID++;
    }

    public synchronized static long generateEventID() {
        return eventID++;
    }
}

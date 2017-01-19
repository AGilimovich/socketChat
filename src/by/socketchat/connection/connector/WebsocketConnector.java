package by.socketchat.connection.connector;

import by.socketchat.connection.ConnectionState;
import by.socketchat.connection.IConnection;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Администратор on 13.12.2016.
 */
public class WebsocketConnector implements IConnector {
    private final String END_OF_LINE_PATTERN = ".*\\r\\n\\r\\n";
    private final String MESSAGE_GET_PATTERN = "^GET";
    private final String SECURITY_MESSAGE_PATTERN = "Sec-WebSocket-Key: (.*)";
    private final String HANDSHAKE_RESPONSE = "HTTP/1.1 101 Switching Protocols\r\n" + "Connection: Upgrade\r\n" + "Upgrade: websocket\r\n" + "Sec-WebSocket-Accept:" + "%s" + "\r\n\r\n";
    private final String KEY = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";

    public boolean connect(IConnection con) {
        return handshake(con);
    }

    private boolean handshake(IConnection con) {
        StringBuilder sb = new StringBuilder();
        Matcher matcher = null;
        Formatter formatter = new Formatter(sb);
        String message = null;
        try {
            message = new String(con.read());
            System.out.println(message);//TODO delete

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!Pattern.compile(END_OF_LINE_PATTERN).matcher(message).find()) {
            System.out.println("no match ENDOFLINE");//TODO delete
            return false;
        }
        if (Pattern.compile(MESSAGE_GET_PATTERN).matcher(message).find()) {
            if ((matcher = Pattern.compile(SECURITY_MESSAGE_PATTERN).matcher(message)).find()) {
                if (formatter == null) {

                    return false;
                    }
                formatter.format(HANDSHAKE_RESPONSE, convert(matcher.group(1)));
                try {
                    System.out.println("sending response");//TODO delete
                    con.write(sb.toString().getBytes("UTF-8"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                con.setState(ConnectionState.CONNECTED);
                return true;
            }
        }
        System.out.println("no match GET MESSAGE");//TODO delete
        return false;
    }

    private String convert(String key) {
        System.out.println("converting");//TODO delete
        try {
            return DatatypeConverter.printBase64Binary(MessageDigest.getInstance("SHA-1").digest((key + KEY).getBytes("UTF-8")));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }


}


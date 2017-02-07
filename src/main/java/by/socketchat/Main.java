package by.socketchat;

import by.socketchat.server.IServer;
import by.socketchat.server.Server;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class Main {

    public static void main(String[] args) {

        ApplicationContext context = new GenericXmlApplicationContext("application-context.xml");
        IServer server = (Server) context.getBean("server");
        server.start();



    }
}

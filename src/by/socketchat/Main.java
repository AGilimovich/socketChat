package by.socketchat;

import by.socketchat.connection.AbstractConnectionFactory;
import by.socketchat.connection.WebSocketConnectionFactory;
import by.socketchat.connection.connector.WebsocketConnector;
import by.socketchat.dao.AbstractDaoFactory;
import by.socketchat.dao.ConcreteDaoFactory;
import by.socketchat.server.Server;
import by.socketchat.entity.message.AbstractMessageFactory;
import by.socketchat.entity.message.ConcreteMessageFactory;
import by.socketchat.formatter.AbstractFormatterFactory;
import by.socketchat.formatter.WebSocketFormatterFactory;
import by.socketchat.service.AbstractServiceFactory;
import by.socketchat.service.WebSocketServiceFactory;

public class Main {

    public static void main(String[] args) {

        AbstractServiceFactory serviceFactory = new WebSocketServiceFactory();
        AbstractDaoFactory DaoFactory = new ConcreteDaoFactory();
        AbstractMessageFactory messageFactory = new ConcreteMessageFactory();
        AbstractFormatterFactory formatterFactory = new WebSocketFormatterFactory();
        AbstractConnectionFactory connectionFactory = new WebSocketConnectionFactory(new WebsocketConnector());

        new Server(8080, serviceFactory, DaoFactory, messageFactory, formatterFactory, connectionFactory).start();


    }
}

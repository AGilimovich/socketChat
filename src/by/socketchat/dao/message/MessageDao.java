package by.socketchat.dao.message;

import by.socketchat.dao.AbstractDao;
import by.socketchat.entity.message.chat.AbstractChatMessage;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Администратор on 27.12.2016.
 */
public class MessageDao extends AbstractDao<AbstractChatMessage> {
    private Set<AbstractChatMessage> messages;

    public MessageDao() {
        messages = new HashSet<>();
    }

    @Override
    public void add(AbstractChatMessage message) {
        messages.add(message);
    }

    @Override
    public void remove(AbstractChatMessage message) {
        messages.remove(message);
    }

    @Override
    public void save(AbstractChatMessage ob) {

    }

    @Override
    public Set<AbstractChatMessage> getAll() {
        return messages;
    }

    @Override
    public AbstractChatMessage findById(long id) {
        Iterator<AbstractChatMessage> it = messages.iterator();
        AbstractChatMessage message = null;
        while (it.hasNext()) {
            message = it.next();
            if (message.getId() == id) {
                return message;
            }
        }
        return null;
    }

    @Override
    public AbstractChatMessage findByName(String name) {
        return null;
    }


}

package ua.nure.kn.teteruk.usermanagment.agent;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.StringTokenizer;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import ua.nure.kn.teteruk.usermanagment.User;
import ua.nure.kn.teteruk.usermanagment.db.DAOFactory;
import ua.nure.kn.teteruk.usermanagment.db.exception.DatabaseException;

public class RequestServer extends CyclicBehaviour {

    @Override
    public void action() {
        ACLMessage message = myAgent.receive();
        if (Objects.nonNull(message)) {
            if (message.getPerformative() == ACLMessage.REQUEST) {
                myAgent.send(createReply(message));
            } else {
                Collection<User> users = parseMessage(message);
                ((SearchAgent) myAgent).showUsers(users);
            }
        } else {
            block();
        }
    }

    private Collection<User> parseMessage(ACLMessage message) {
        Collection<User> users = new LinkedList<>();

        String content = message.getContent();
        if (Objects.nonNull(content)) {
            StringTokenizer tokenizer1 = new StringTokenizer(content, ",");
            while (tokenizer1.hasMoreTokens()) {
                String userInfo = tokenizer1.nextToken();
                StringTokenizer tokenizer2 = new StringTokenizer(userInfo, ",");
                String id = tokenizer2.nextToken();
                String firstName = tokenizer2.nextToken();
                String lastName = tokenizer2.nextToken();
                users.add(new User(new Long(id), firstName, lastName, null));
            }
        }
        return users;
    }

    private ACLMessage createReply(ACLMessage message) {
        ACLMessage reply = message.createReply();

        String contet = message.getContent();
        StringTokenizer tokenizer = new StringTokenizer(contet, ",");
        if (tokenizer.countTokens() == 2) {
            String firstName = tokenizer.nextToken();
            String lastName = tokenizer.nextToken();
            Collection<User> users = null;
            try {
                users = DAOFactory.getInstance().getUserDao().find(firstName, lastName);
            } catch (DatabaseException e) {
                e.printStackTrace();
                users = Collections.emptyList();
            }
            StringBuffer buffer = new StringBuffer();
            for (Iterator it = users.iterator(); it.hasNext(); ) {
                User user = (User) it.next();
                buffer.append(user.getId()).append(",")
                        .append(user.getFirstName()).append(",")
                        .append(user.getLastName()).append(";");
            }
            reply.setContent(buffer.toString());
        }
        return reply;
    }
}

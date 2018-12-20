package ua.nure.kn.teteruk.usermanagment.agent;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
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
        return null;
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

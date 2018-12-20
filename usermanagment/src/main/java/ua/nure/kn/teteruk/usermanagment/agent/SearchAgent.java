package ua.nure.kn.teteruk.usermanagment.agent;

import java.util.Collection;

import org.dbunit.util.search.SearchException;

import jade.core.AID;
import jade.core.Agent;
import ua.nure.kn.teteruk.usermanagment.User;
import ua.nure.kn.teteruk.usermanagment.db.DAOFactory;
import ua.nure.kn.teteruk.usermanagment.db.exception.DatabaseException;

public class SearchAgent extends Agent {
    @Override
    protected void setup() {
        super.setup();
        System.out.println(getAID().getName() + " started");
    }

    @Override
    protected void takeDown() {
        System.out.println(getAID().getName() + " terminated");
        super.takeDown();
    }

    public void search(String firstName, String lastName) throws SearchException {
        try {
            Collection<User> users = DAOFactory.getInstance().getUserDao().find(firstName, lastName);
            if (!users.isEmpty()) {
                showUsers(users);
            } else {
                addBehaviour(new SearchRequestBehaviour(new AID[] {}, firstName, lastName));
            }
        } catch (DatabaseException e) {
            throw new SearchException(e);
        }
    }

    private void showUsers(Collection<User> users) {
        // todo implement method
    }
}

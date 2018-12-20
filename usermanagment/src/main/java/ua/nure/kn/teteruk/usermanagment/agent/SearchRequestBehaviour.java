package ua.nure.kn.teteruk.usermanagment.agent;

import java.util.Objects;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class SearchRequestBehaviour extends Behaviour {

    private AID[] aids;
    private String firstName;
    private String lastName;

    public SearchRequestBehaviour(AID[] aids, String firstName, String lastName) {
        this.aids = aids;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public void action() {
        if (Objects.nonNull(aids)) {
            ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
            message.setContent(firstName + "," + lastName);
            for (AID aid : aids) {
                message.addReceiver(aid);
            }
            myAgent.send(message);
        }
    }

    @Override
    public boolean done() {
        return true;
    }
}

package projects.dm1.nodes.nodeImplementations;

import java.awt.Color;



import projects.dm1.nodes.messages.APDMessage;
import projects.dm1.nodes.messages.TypeMessage;
import projects.dm1.nodes.timers.InitTimer;


/** the initiator node sends the message (the walker) */
public class InitNode extends APDNode {

	/* InitNode() { ... } */
	public void init() {
		super.init(); 
		setColor(Color.YELLOW);
		(new InitTimer(this)).startRelative(InitTimer.timerRefresh, this); 		
	}

	public void initiate() {
		APDMessage message1 = new APDMessage(TypeMessage.ASK, ID, 0, true);
		APDMessage message2 = new APDMessage(TypeMessage.ASK, ID, 0, false);
		//System.out.println(this + " is sending now message " + message1);
		
		
		send(message1, premierVoisin());
		send(message2, secondVoisin());
	}

	public String toString() {
		return super.toString() + "(init)";
	}
}

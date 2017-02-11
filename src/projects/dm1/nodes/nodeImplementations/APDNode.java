package projects.dm1.nodes.nodeImplementations;

import java.awt.Color;
import java.util.Random;
import projects.dm1.nodes.messages.APDMessage;
import projects.dm1.nodes.messages.TypeMessage;
import sinalgo.nodes.Node;
import sinalgo.nodes.edges.Edge;
import sinalgo.tools.storage.ReusableListIterator;

public class APDNode extends sinalgo.nodes.Node {

	/* WalkerNode() { 
	 *   // no constructor code, it breaks the way sinalgo builds the nodes. 
	 *   // instead use the init() method 
	 * }
	 * */
	
	private int l = 1;
	
	private int n = 0;
	
	private boolean resultat;
	
	
	public void init() {
		setColor(Color.YELLOW);
	}
		
	public String toString() {
		return " " + ID + " "; 
	}
	
	protected Node premierVoisin(){
		ReusableListIterator<Edge> it = outgoingConnections.iterator();
		
		return it.next().endNode;
	}
	
	protected Node secondVoisin(){
		ReusableListIterator<Edge> it = outgoingConnections.iterator();
		it.next();
		return it.next().endNode;
	}

	public void handleMessages(sinalgo.nodes.messages.Inbox inbox) {
		while(inbox.hasNext()) {
			sinalgo.nodes.messages.Message msg = inbox.next();
			if (msg instanceof APDMessage) {
				APDMessage message = (APDMessage) msg;
				
				if (message.getType()==TypeMessage.ASK) {
					
					if(message.getId()==ID){
						resultat=true;
						setColor(Color.GREEN);
					} else {
						if (message.getTtl()>0){
							if(message.getId()>ID){
								
								if(message.getSens()){
									send (new APDMessage(TypeMessage.ASK, message.getId(), message.getTtl()-1, true), premierVoisin());
								} else {
									send (new APDMessage(TypeMessage.ASK, message.getId(), message.getTtl()-1, false), secondVoisin());
								}
			
							} else {
								resultat=false;
								setColor(Color.BLUE);
							}
							
						} else {
							if(message.getId()>ID){
								//On doit renvoyer au destinataire cette fois
								if(message.getSens()){
									send (new APDMessage(TypeMessage.REPLY, message.getId(), 0, false), secondVoisin());
								} else {
									send (new APDMessage(TypeMessage.REPLY, message.getId(), 0, true), premierVoisin());
								}
							} else {
								resultat=false;
								setColor(Color.BLUE);
							}
						}
					}
						
					
					
				} else if (message.getType()==TypeMessage.REPLY) {
					if(message.getId()!=ID){
						//TODO l30 Je ne comprend pas à quoi correspond M
					} else {
						n++;
						if(n==2){
							n=0;
							l*=2;
							send(new APDMessage(TypeMessage.ASK, ID, l-1, true), premierVoisin());
							send(new APDMessage(TypeMessage.ASK, ID, l-1, false), secondVoisin());
						}
					}
				}
				
				
				
				
				
				
				
				
				
				
				
				
				Node next = randomWalkChoice(outgoingConnections);		
				send(message, next);
				System.out.println(this + " received message " + message + 
								   " and sends it now to " + next);
			}
		}
	}
	
	private static Random random = new Random();
	Node randomWalkChoice(sinalgo.nodes.Connections neighbors) {
		int degree = neighbors.size();
		if (degree == 0) throw new RuntimeException("no neighbor");
		int positionOfNext = random.nextInt(degree);
		sinalgo.tools.storage.ReusableListIterator<sinalgo.nodes.edges.Edge> iter 
			= neighbors.iterator();
		Node node = iter.next().endNode;
		for (int i = 1; i <= positionOfNext; i++) node = iter.next().endNode;
		return node;
	}

	
	public void preStep() {};
	public void neighborhoodChange() {};
	public void postStep() {}; 
	public void checkRequirements() throws sinalgo.configuration.WrongConfigurationException {};
	public void draw(java.awt.Graphics g, sinalgo.gui.transformation.PositionTransformation pt, 
					 boolean highlight) {
		// draw the node as a circle with the text inside
		super.drawNodeAsDiskWithText(g, pt, highlight, toString(), 20, Color.black);
	}
}
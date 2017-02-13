package projects.dm1.nodes.nodeImplementations;

import java.awt.Color;
import java.util.Random;
import projects.dm1.nodes.messages.AskMessage;
import projects.dm1.nodes.messages.ReplyMessage;
import projects.dm1.nodes.messages.Sens;
import projects.dm1.nodes.timers.InitTimer;
import sinalgo.nodes.Node;
import sinalgo.nodes.edges.Edge;
import sinalgo.tools.storage.ReusableListIterator;

public class APDNode extends sinalgo.nodes.Node {

	/*
	 * WalkerNode() { // no constructor code, it breaks the way sinalgo builds
	 * the nodes. // instead use the init() method }
	 */

	private static int compteur = 0;

	private int l;

	private int n;

	private boolean resultat;

	public APDNode() {
		l = 1;
		n = 0;
		ID = compteur++;
	}

	/* InitNode() { ... } */
	public void init() {
		setColor(Color.YELLOW);
	}

	public void initiate() {
		AskMessage messageDroit = new AskMessage(ID, 0, Sens.DROIT);
		AskMessage messageGauche = new AskMessage(ID, 0, Sens.GAUCHE);

		send(messageDroit, voisinDroit());
		send(messageGauche, voisinGauche());
		
		System.out.println( ID + " emet ASK vers " + voisinDroit().ID + "\n");
		System.out.println( ID + " emet ASK vers " + voisinGauche().ID + "\n");
	}

	public String toString() {
		return " " + ID + " ";
	}

	protected Node voisinDroit() {
		ReusableListIterator<Edge> it = outgoingConnections.iterator();

		return it.next().endNode;
	}

	protected Node voisinGauche() {
		ReusableListIterator<Edge> it = outgoingConnections.iterator();
		it.next();
		return it.next().endNode;
	}

	public void handleMessages(sinalgo.nodes.messages.Inbox inbox) {
		while (inbox.hasNext()) {
			sinalgo.nodes.messages.Message msg = inbox.next();

			if (msg instanceof AskMessage) {
				AskMessage message = (AskMessage) msg;
				
				System.out.println(ID + " recoit ASK de " + message.getId() + " avec ttl " + message.getTtl() + "\n");

				if (message.getId() == ID) {
					resultat = true;
					setColor(Color.GREEN);
					
					System.out.println(ID + " est élu.\n");
				} else {
					if (message.getTtl() > 0) {
						if (message.getId() > ID) {

							message.decrementTtl();

							if (message.getSens() == Sens.DROIT) {
								send(msg, voisinDroit());
								System.out.println(ID + " emet ASK de " + message.getId() + " avec ttl " + message.getTtl() + " vers " + voisinDroit().ID + "\n");

							} else {
								send(msg, voisinGauche());
								System.out.println(ID + " emet ASK de " + message.getId() + " avec ttl " + message.getTtl() + " vers " + voisinGauche().ID + "\n");

							}

						} else {
							resultat = false;
							System.out.println(ID + " n'est pas élu (cond 1), ID recu " + message.getId() + ".\n");
							setColor(Color.BLUE);
						}
					}

					else {
						if (message.getId() > ID) {

							// On doit renvoyer au destinataire cette fois
							if (message.getSens() == Sens.DROIT) {
								send(new ReplyMessage(message.getId(), Sens.GAUCHE), voisinGauche());
								System.out.println(ID + " emet Reply de " + message.getId() + " vers " + voisinGauche().ID + "\n");

							} else {
								send(new ReplyMessage(message.getId(), Sens.DROIT), voisinDroit());
								System.out.println(ID + " emet Reply de " + message.getId() + " vers " + voisinDroit().ID + "\n");

							}
						} else {
							resultat = false;
							System.out.println(ID + " n'est pas élu (cond 2), ID recu " + message.getId() + ".\n");
							setColor(Color.BLUE);
						}
					}
				}

			} else {
				if (msg instanceof ReplyMessage) {
					ReplyMessage message = (ReplyMessage) msg;
					System.out.println(ID + " recoit Reply de " + message.getId() + "\n");
					
					if (message.getId() != ID) {
						// On fait juste suivre le message reçu, merci l'algo
						// d'être
						// explicite sur ce qu'est M, vraiment MERCI !
						if (message.getSens() == Sens.DROIT) {
							send(msg, voisinDroit());
							System.out.println(ID + " transmet Reply de " + message.getId() + " vers " + voisinDroit().ID + "\n");

						} else {
							send(msg, voisinGauche());
							System.out.println(ID + " transmet Reply de " + message.getId() + " vers " + voisinGauche().ID + "\n");

						}
					} else {

						if (message.getId() == ID) { // probablement le test le
														// plus utile de
														// l'histoire de
														// l'informatique
							n++;
							if (n == 2) {
								n = 0;
								l = l * 2;
								send(new AskMessage(ID, l - 1, Sens.DROIT), voisinDroit());
								System.out.println(ID + " emet ASK de " + message.getId() + " avec ttl " + (l-1) + " vers " + voisinDroit().ID + "\n");

								send(new AskMessage(ID, l - 1, Sens.GAUCHE), voisinGauche());
								System.out.println(ID + " emet ASK de " + message.getId() + " avec ttl " + (l-1) + " vers " + voisinDroit().ID + "\n");

							}
						}

					}
				}

			}
		}
	}

	public void preStep() {
	};

	public void postStep() {
	};

	public void checkRequirements() throws sinalgo.configuration.WrongConfigurationException {
	};

	public void draw(java.awt.Graphics g, sinalgo.gui.transformation.PositionTransformation pt, boolean highlight) {
		// draw the node as a circle with the text inside
		super.drawNodeAsDiskWithText(g, pt, highlight, toString(), 20, Color.black);
	}

	@Override
	public void neighborhoodChange() {
		// TODO Auto-generated method stub

	}
}
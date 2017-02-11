package projects.dm1.nodes.messages;

import sinalgo.nodes.messages.Message;

public class APDMessage extends Message {
	
	private TypeMessage type;
	
	private int id;
	
	//Le 3e champs des messages (je le comprend comme un TTL de réséau
	private int ttl;

	private static int msgCounter = 0;
	private int msgId;
	
	//Je définie l'envoie au premier voisin comme un envoie dans le "bon"sens 
	//Je définie l'envoie au second voisin comme un envoie dans le "mauvais"sens 
	
	private boolean sens;
	
	public APDMessage( TypeMessage type, int idEmetteur, int ttl, boolean sens) {
		super();
		msgId = msgCounter;
		msgCounter++;
		
		this.type = type;
		this.id=idEmetteur;
		this.ttl = ttl;
		this.sens=sens;
	}
	
	

	public Message clone() {
		return this;
	}
	
	public String toString() {
		return "apd" + msgId;
	}

	public int getTtl() {
		return ttl;
	}

	public int getId() {
		return id;
	}
	
	public TypeMessage getType() {
		return type;
	}
	
	public boolean getSens(){
		return this.sens;
	}

} 
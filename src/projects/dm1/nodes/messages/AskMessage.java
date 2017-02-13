package projects.dm1.nodes.messages;

import sinalgo.nodes.messages.Message;

public class AskMessage extends Message {
		
	//Le 3e champs des messages (je le comprend comme un TTL de réséau
	private int ttl;

	private int msgId;
	
	private Sens sens;
	
	public AskMessage(  int idEmetteur, int ttl, Sens sens) {
		super();
		/*msgId = msgCounter;
		msgCounter++;*/
		
		this.msgId=idEmetteur;
		this.ttl=ttl;
		
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
		return msgId;
	}
	
	public Sens getSens(){
		return this.sens;
	}

} 
package projects.dm1.nodes.messages;

import sinalgo.nodes.messages.Message;

public class ReplyMessage extends Message {
		


	private int msgId;
	
	private Sens sens;
	
	public ReplyMessage(  int idEmetteur, Sens sens) {
		super();
		/*msgId = msgCounter;
		msgCounter++;*/
		
		this.msgId=idEmetteur;
	
		
		this.sens=sens;
	}
	
	

	public Message clone() {
		return this;
	}
	
	public String toString() {
		return "apd" + msgId;
	}

	public int getId() {
		return msgId;
	}
	
	public Sens getSens(){
		return this.sens;
	}

} 
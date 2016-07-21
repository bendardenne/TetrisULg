package network;

import java.io.Serializable;

public class Message implements Serializable {

	private final int sourceID;
	private final MessageType type;
	private final Serializable[] args;
	
	public Message(int sourceID, MessageType type, Serializable[] args)
	{
		this.sourceID = sourceID;
		this.type = type;
		this.args = args;
	}
	
	public int getSource() { return sourceID; } 
	public MessageType getType() { return type; } 
	public Serializable[] getArgs() { return args; } 

}

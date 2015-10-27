package se.umu.cs.dist.ht15.dali_ens15bsf.ordering;

import se.umu.cs.dist.ht15.dali_ens15bsf.Message;

import java.util.Queue; 
import java.util.LinkedList;

public class UnorderedStrategy extends Orderer {

	@Override
	public void addMessage(Message msg) {
		deliver(msg, msg.getId());
	}

	@Override
	public Message prepareMessage ( Message msg )
	{
		return msg;
	}

	private void deliver(Message m, String senderId) {
		setChanged();
		notifyObservers(m);
	}

	@Override
	public Queue getHoldbackQueue(String id) {
		return new LinkedList<Message>();
	}

}

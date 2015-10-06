package se.umu.cs.dist.ht15.dali_ens15bsf.ordering;

import se.umu.cs.dist.ht15.dali_ens15bsf.Message;
import se.umu.cs.dist.ht15.dali_ens15bsf.time.VectorClock;

import java.util.Observable;
import java.util.Queue; 
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;

public class CausalOrderer extends Observable implements Orderer {
	private Map<String, Queue> holdbackQueues;

	public CausalOrderer() {
		holdbackQueues = new HashMap<String, Queue>();
	}

	@Override
	public void addMessage(Message msg) {
		if(msg.getClock() == null) {
			throw new NullPointerException("Message clock was null");
		}

		if(!holdbackQueues.containsKey(msg.getId()))
			holdbackQueues.put(msg.getId(), new LinkedList<Message>());

		holdbackQueues.get(msg.getId()).add(msg);


	}

	@Override
	public Queue getHoldbackQueue(String id) {
		return holdbackQueues.get(id);
	}

}

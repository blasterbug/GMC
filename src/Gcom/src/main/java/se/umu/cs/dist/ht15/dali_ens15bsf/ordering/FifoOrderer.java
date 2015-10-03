package se.umu.cs.dist.ht15.dali_ens15bsf.ordering;

import se.umu.cs.dist.ht15.dali_ens15bsf.Message;
import java.util.Queue;
import java.util.HashMap;
import java.util.Map;
import java.util.LinkedList;
import se.umu.cs.dist.ht15.dali_ens15bsf.time.VectorClock;

public class FifoOrderer implements Orderer {
	private Map<String, Queue> holdbackQueues; 
	private VectorClock orderClock;

	public FifoOrderer() {
		holdbackQueues = new HashMap<String, Queue>();
		orderClock = new VectorClock();
	}

	@Override
	public void addMessage(Message msg) {
		String senderId = msg.getId();
		VectorClock senderClock = msg.getClock();

		if(!holdbackQueues.containsKey(senderId)) {
			holdbackQueues.put(senderId, new LinkedList<Message>());
		}

		Integer senderSeqNr = senderClock.get(senderId);
		Integer orderSeqNr = orderClock.get(senderId);

		Queue q = holdbackQueues.get(senderId);
		if ( orderSeqNr.equals(0) ||
			senderSeqNr.equals(orderSeqNr.intValue()+1)) {
			orderClock.updateTime(senderId, senderSeqNr);
			for 

		} else {
			q.add(msg);
		}


	}

	private void deliver() {

	}

	@Override
	public Queue getHoldbackQueue(String id) {
		return holdbackQueues.get(id);
	}
}

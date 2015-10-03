package se.umu.cs.dist.ht15.dali_ens15bsf.ordering;

import se.umu.cs.dist.ht15.dali_ens15bsf.Message;
import java.util.Queue;
import java.util.HashMap;
import java.util.Map;
import java.util.LinkedList;
import se.umu.cs.dist.ht15.dali_ens15bsf.time.VectorClock;

public class FifoOrderer implements Orderer {
	private Map<String, Queue> orderings; 
	private VectorClock orderClock;

	public FifoOrderer() {
		orderings = new HashMap<String, Queue>();
		orderClock = new VectorClock();
	}

	@Override
	public void addMessageToOrder(Message msg) {
		String msgId = msg.getId();
		if(!orderings.containsKey(msgId)) {
			orderings.put(msgId, new LinkedList<Message>());
		}
		Queue q = orderings.get(msgId);
		q.add(msg);

	}

	private void deliver() {

	}

	@Override
	public Queue getHoldbackQueue(String id) {
		return orderings.get(id);
	}
}

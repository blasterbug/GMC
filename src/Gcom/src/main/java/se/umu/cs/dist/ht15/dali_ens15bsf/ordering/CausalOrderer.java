package se.umu.cs.dist.ht15.dali_ens15bsf.ordering;

import se.umu.cs.dist.ht15.dali_ens15bsf.CausalMessage;
import se.umu.cs.dist.ht15.dali_ens15bsf.Message;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.time.VectorClock;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class CausalOrderer extends Orderer {
	private Map<String, Queue> holdbackQueues;
	private VectorClock orderClock;

	public CausalOrderer() {
		holdbackQueues = new HashMap<String, Queue>();
		orderClock = new VectorClock();
	}

	@Override
	public void addMessage(Message msg) {
		System.out.println("Receiving "+msg);	
		VectorClock senderClock = ((CausalMessage)msg).getClock();
		System.out.println(senderClock.toString());
		System.out.println(orderClock.toString());	
		String senderId = msg.getId();

		Integer senderSeqNr = senderClock.get(senderId);

		if(senderClock == null) {
			throw new NullPointerException("Message clock was null");
		}

		if(!holdbackQueues.containsKey(senderId))
			holdbackQueues.put(senderId, new LinkedList<Message>());

		Queue q = holdbackQueues.get(senderId);

		/* INCOMING: Terrible code, needs refactoring */
		System.out.println(senderClock.compare(orderClock));	
		if ((senderSeqNr == (orderClock.get(senderId)+1) &&
				orderClock.compare(senderClock) < 1)) {
			System.out.println("DELIVERING FIRST");	
			deliver(msg, senderId);
		} else {
			q.add(msg);
		}
	}

	@Override
	public Message prepareMessage ( Message msg )
	{
		VectorClock cl = new VectorClock(orderClock);
		cl.increment(msg.getId());
		CausalMessage c = new CausalMessage(msg, cl);
		return c;
	}

	private void deliver(Message m, String senderId) {
		System.out.println("Delivering "+m);	

		orderClock.updateClock(((CausalMessage)m).getClock());
		setChanged();
		notifyObservers(m);
		clearMessages(senderId);
	}

	private void clearMessages(String senderId) {
		boolean didChange;
		int removed;
		do{
			didChange = false;
			removed = 0;
			for (Queue queue : holdbackQueues.values()) {
				for (int i = 0; i < queue.size()-removed; i++) {
					CausalMessage m = (CausalMessage) queue.peek();
					String id =  m.getId();
					
					Integer sendSeq = m.getClock().get(id);
					Integer orderSeq = orderClock.get(id);
					if(sendSeq == (orderSeq+1)) {
						if(orderClock.compare(m.getClock()) < 1){
							System.out.println("SECOND DELIVERING");	
							deliver((CausalMessage) queue.remove(), id);
							didChange = true;
							removed++;
						}
					}
				}
			}
		} while(didChange);
	}

	@Override
	public Queue getHoldbackQueue(String id) {
		return holdbackQueues.get(id);
	}

}

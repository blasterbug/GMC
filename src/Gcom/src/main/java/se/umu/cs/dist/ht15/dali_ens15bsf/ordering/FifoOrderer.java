package se.umu.cs.dist.ht15.dali_ens15bsf.ordering;

import se.umu.cs.dist.ht15.dali_ens15bsf.Message;
import se.umu.cs.dist.ht15.dali_ens15bsf.FifoMessage;
import java.util.Queue;
import java.util.HashMap;
import java.util.Map;
import java.util.LinkedList;
import se.umu.cs.dist.ht15.dali_ens15bsf.time.VectorClock;
import java.util.Observable;

public class FifoOrderer extends Orderer {
	private Map<String, Queue> holdbackQueues; 
	private VectorClock orderClock;

	public FifoOrderer() {
		holdbackQueues = new HashMap<String, Queue>();
		orderClock = new VectorClock();
	}

	@Override
	public void addMessage(Message msg) {
		String senderId = msg.getId();
		VectorClock senderClock = ((FifoMessage)msg).getClock();

		if(!holdbackQueues.containsKey(senderId)) 
			holdbackQueues.put(senderId, new LinkedList<Message>());


		Integer senderSeqNr = senderClock.get(senderId);
		Integer orderSeqNr = orderClock.get(senderId);

		Queue q = holdbackQueues.get(senderId);

		if (senderSeqNr.equals(orderSeqNr)) {
			orderClock.updateTime(senderId, senderSeqNr);
			deliver(msg, senderId);
			/* Delivers all messages with seq. nr. lower than seq. just cleared */
			deliverClearMessageSequence(q, senderId);
		} else {
			q.add(msg);
		}
	}

	@Override
	public Message prepareMessage ( Message msg )
	{
		VectorClock c = new VectorClock(orderClock);
		c.increment(msg.getId());
		FifoMessage fifo = new FifoMessage(msg, c);
		
		return fifo;
	}

	private void deliverClearMessageSequence(Queue q, String senderId) {
		boolean didChange;
		int removed;
		do{
			didChange = false;
			removed = 0;
			for (int i = 0; i < q.size()-removed; i++) {
				FifoMessage m = (FifoMessage) q.poll();
				if(m.getClock().get(senderId)
						<= orderClock.get(senderId)) {
					deliver(m, senderId);
					didChange = true;
					removed++;

				} else {
					q.add(m);
				}
			}
		}while(didChange);
	}


	private void deliver(Message m, String senderId) {
		orderClock.increment(senderId);
		setChanged();
		notifyObservers(m);
	}

	@Override
	public Queue getHoldbackQueue(String id) {
		return holdbackQueues.get(id);
	}
}

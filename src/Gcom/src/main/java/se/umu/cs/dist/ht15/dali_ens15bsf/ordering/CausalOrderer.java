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
	private VectorClock orderClock;

	public CausalOrderer() {
		holdbackQueues = new HashMap<String, Queue>();
		orderClock = new VectorClock();
	}

	@Override
	public void addMessage(Message msg) {
		VectorClock senderClock = msg.getClock();
		String senderId = msg.getId();

		Integer senderSeqNr = senderClock.get(senderId);
		Integer orderSeqNr = orderClock.get(senderId);


		if(senderClock == null) {
			throw new NullPointerException("Message clock was null");
		}

		if(!holdbackQueues.containsKey(senderId))
			holdbackQueues.put(senderId, new LinkedList<Message>());

		Queue q = holdbackQueues.get(senderId);

		boolean didDeliver = false;

		/* INCOMING: Terrible code, needs refactoring */
		if (senderSeqNr == (orderSeqNr+1)) {
			senderClock.updateTime(senderId, orderSeqNr);
			if (VectorClock.compare(senderClock, orderClock) <= 0) {
				deliver(msg, senderId);
				orderClock.increment(senderId);
				didDeliver = true;

			} else {
				q.add(msg);
			}
			senderClock.increment(senderId);
				
		} else {
			q.add(msg);
		}

		if (didDeliver) {
			boolean didChange;
			int removed;
			do{
				didChange = false;
				removed = 0;
				for (int i = 0; i < q.size()-removed; i++) {
					Message m = (Message) q.poll();
					String id =  m.getId();
					Integer sendSeq = m.getClock().get(id);
					Integer orderSeq = orderClock.get(id);
					if(sendSeq == (orderSeq+1)) {
						m.getClock().updateTime(id, orderSeq);
						if(VectorClock.compare(m.getClock(), orderClock) <= 0){
							deliver(m, id);
							didChange = true;
							removed++;
							orderClock.increment(id);
						} else {
							q.add(m);
						}

					} else {
						q.add(m);
					}
				}
			}while(didChange);


		}






	}
	private void deliver(Message m, String id) {
		//System.out.println("Delivering message: "+m.getContent());

	}

	private void deliverClearMessageSequence(Queue q, String senderId) {
		boolean didChange;
		int removed;
		do{
			didChange = false;
			removed = 0;
			for (int i = 0; i < q.size()-removed; i++) {
				Message m = (Message) q.poll();
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


	@Override
	public Queue getHoldbackQueue(String id) {
		return holdbackQueues.get(id);
	}

}

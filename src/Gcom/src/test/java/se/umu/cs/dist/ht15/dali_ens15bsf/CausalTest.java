package se.umu.cs.dist.ht15.dali_ens15bsf;

import org.junit.Test;
import junit.framework.Assert;

import se.umu.cs.dist.ht15.dali_ens15bsf.time.VectorClock;
import se.umu.cs.dist.ht15.dali_ens15bsf.ordering.Orderer;
import se.umu.cs.dist.ht15.dali_ens15bsf.ordering.CausalOrderer;

import java.util.Observer;
import java.util.Observable;
import java.util.LinkedList;


public class CausalTest {

	private class DummyObserver implements Observer {
		private Observable observable;
		private LinkedList<Message> messages;

		public DummyObserver(Observable o) {
			observable = o;
			messages = new LinkedList<Message>();
		}

		@Override
		public void update(Observable obs, Object o) {
			Message msg = (Message)o;
			messages.add((Message)o);
		}

		public boolean contains(Message m) {
			return messages.contains(m);
		}

		public boolean containsAt(Message m, int i) {
			if(contains(m))
				return messages.get(i).equals(m);
			return false;
		}
	}

	@Test
	public void shouldReturnNullHoldbackAfterInit() {
		Orderer causal = new CausalOrderer();
		Assert.assertTrue(causal.getHoldbackQueue("id1") == null);
	}

	@Test(expected=NullPointerException.class)
	public void shouldThrowNullPtrExceptOnNullClock() {
		Orderer causal = new CausalOrderer();
		Message msg = new Message("id1", null, null);
		causal.addMessage(msg);
		
		Assert.assertTrue(causal.getHoldbackQueue("id1") != null);
	}

	@Test
	public void shouldNotReturnNullHoldbackAfterMessage() {
		Orderer causal = new CausalOrderer();
		VectorClock clock = new VectorClock();
		Message msg = new Message("id1", null, clock);
		causal.addMessage(msg);
		
		Assert.assertTrue(causal.getHoldbackQueue("id1") != null);
	}


}



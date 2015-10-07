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
		clock.increment("id1");
		Message msg = new Message("id1", null, clock);
		causal.addMessage(msg);
		
		Assert.assertTrue(causal.getHoldbackQueue("id1") != null);
	}

	@Test
	public void shouldNotHoldFirstEverMessage() {
		Orderer causal = new CausalOrderer();
		VectorClock clock = new VectorClock();
		clock.increment("id1");
		Message msg = new Message("id1", null, clock);

		causal.addMessage(msg);

		Assert.assertTrue(causal.getHoldbackQueue("id1").isEmpty());
	}

	@Test
	public void shouldHoldFutureMessage() {
		Orderer causal = new CausalOrderer();
		VectorClock clock = new VectorClock();
		clock.increment("id1");
		clock.increment("id1");
		Message msg = new Message("id1", null, clock);

		causal.addMessage(msg);

		Assert.assertTrue(!causal.getHoldbackQueue("id1").isEmpty());
	}
	@Test
	public void shouldNotHoldFutureMessageOnceReached() {
		Orderer causal = new CausalOrderer();
		VectorClock clock = new VectorClock();
		clock.increment("id1");
		clock.increment("id1");
		Message msg = new Message("id1", null, clock);

		causal.addMessage(msg);

		VectorClock clock2 = new VectorClock();
		clock2.increment("id1");
		Message msg2 = new Message("id1", null, clock2);

		causal.addMessage(msg2);

		Assert.assertTrue(causal.getHoldbackQueue("id1").isEmpty());
	}

	@Test
	public void shouldNotHoldWithHigherOtherSeqNr() {
		Orderer causal = new CausalOrderer();

		VectorClock c0 = new VectorClock();
		c0.increment("id2");

		/* Setup scenario */
		VectorClock c1 = new VectorClock();
		c1.updateTime("id2", 2);

		VectorClock c2 = new VectorClock();
		c2.updateTime("id2", 3);

		Message m0 = new Message("id2", "m0", c0);
		Message m1 = new Message("id2", "m1", c1);
		Message m2 = new Message("id2", "m2", c2);


		VectorClock clock = new VectorClock();
		clock.updateTime("id1", 2);
		clock.updateTime("id2", 3);
		Message msg = new Message("id1", "m4", clock);

		VectorClock clock2 = new VectorClock();
		clock2.updateTime("id1",1);
		clock2.updateTime("id2", 3);

		Message msg2 = new Message("id1", "m4", clock2);

		causal.addMessage(m0);
		causal.addMessage(m1);
		causal.addMessage(m2);
		causal.addMessage(msg);
		causal.addMessage(msg2);

		Assert.assertTrue(causal.getHoldbackQueue("id1").isEmpty());

	}

	@Test
	public void shouldDeliverFirstEverMessage() {
		CausalOrderer causal = new CausalOrderer();

		DummyObserver dummy = new DummyObserver(causal);

		causal.addObserver(dummy);

		VectorClock c1 = new VectorClock();
		c1.increment("id1");

		Message m1 = new Message("id1", "m1", c1);

		causal.addMessage(m1);

		Assert.assertTrue(dummy.contains(m1));


	}

	@Test
	public void shouldDeliverWithHigherOtherSeqNr() {
		CausalOrderer causal = new CausalOrderer();
		DummyObserver dummy = new DummyObserver(causal);
		causal.addObserver(dummy);

		VectorClock c0 = new VectorClock();
		c0.increment("id2");

		/* Setup scenario */
		VectorClock c1 = new VectorClock();
		c1.updateTime("id2", 2);

		VectorClock c2 = new VectorClock();
		c2.updateTime("id2", 3);

		Message m0 = new Message("id2", "m0", c0);
		Message m1 = new Message("id2", "m1", c1);
		Message m2 = new Message("id2", "m2", c2);


		VectorClock clock = new VectorClock();
		clock.updateTime("id1", 2);
		clock.updateTime("id2", 3);
		Message msg = new Message("id1", "m4", clock);

		VectorClock clock2 = new VectorClock();
		clock2.updateTime("id1",1);
		clock2.updateTime("id2", 3);

		Message msg2 = new Message("id1", "m4", clock2);

		causal.addMessage(m0);
		causal.addMessage(m1);
		causal.addMessage(m2);
		causal.addMessage(msg);
		causal.addMessage(msg2);

		Assert.assertTrue(dummy.containsAt(m0, 0) &&
				dummy.containsAt(m1, 1) &&
				dummy.containsAt(m2, 2) &&
				dummy.containsAt(msg, 3) &&
				dummy.containsAt(msg2, 4));
	}

}



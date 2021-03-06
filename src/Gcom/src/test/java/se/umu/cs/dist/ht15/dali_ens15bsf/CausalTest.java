package se.umu.cs.dist.ht15.dali_ens15bsf;

import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;
import se.umu.cs.dist.ht15.dali_ens15bsf.ordering.CausalOrderer;
import se.umu.cs.dist.ht15.dali_ens15bsf.ordering.Orderer;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.time.VectorClock;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;


public class CausalTest {

	private class DummyObserver implements Observer {
		private LinkedList<CausalMessage> messages;

		public DummyObserver() {
			messages = new LinkedList<CausalMessage>();
		}

		@Override
		public void update(Observable obs, Object o) {
			messages.add((CausalMessage)o);
		}

		public boolean contains(Message m) {
			return messages.contains(m);
		}

		public boolean containsAt(Message m, int i) {
			/*
			System.out.println("MSG: "+messages.get(i).getContent());
			System.out.println("MSG2: " + m.getContent());
			System.out.println("MSG: "+messages.get(i).getId());
			System.out.println("MSG2: " + m.getId());
			System.out.println("MSG: "+messages.get(i).getClock());
			System.out.println("MSG2: " + ((CausalMessage)m).getClock());

			System.out.println(m.equals(messages.get(i)));	
			System.out.println(messages.get(i).equals(m));	
			*/
			if(messages.size() > i) 
				return messages.get(i).getId().equals(m.getId()) && 
					messages.get(i).getContent().equals(m.getContent());
			return false;
		}

		public void printMessages() {
			int i = 0;
			System.out.println("PRINTING DUMMY MESSAGES");
			for ( Message m : messages) {
				System.out.println("Message ["+i++ +"]: "+m.getContent());
			}
		}
	}

	
	@Ignore
	@Test
	public void shouldReturnNullHoldbackAfterInit() {
		Orderer causal = new CausalOrderer();
		Assert.assertTrue(causal.getHoldbackQueue("id1") == null);
	}

	@Ignore
	@Test(expected=NullPointerException.class)
	public void shouldThrowNullPtrExceptOnNullClock() {
		Orderer causal = new CausalOrderer();
		VectorClock clock = null;
		Message msg = new CausalMessage(new Message("id1", null), clock);

		causal.addMessage(msg);
		
		Assert.assertTrue(causal.getHoldbackQueue("id1") != null);
	}

	@Ignore
	@Test
	public void shouldNotReturnNullHoldbackAfterMessage() {
		Orderer causal = new CausalOrderer();
		VectorClock clock = new VectorClock();
		clock.increment("id1");
		Message msg = new CausalMessage(new Message("id1", null), clock);
		causal.addMessage(msg);
		
		Assert.assertTrue(causal.getHoldbackQueue("id1") != null);
	}

	@Ignore
	@Test
	public void shouldNotHoldFirstEverMessage() {
		Orderer causal = new CausalOrderer();
		VectorClock clock = new VectorClock();
		clock.increment("id1");
		Message msg = new CausalMessage(new Message("id1", null), clock);

		causal.addMessage(msg);

		Assert.assertTrue(causal.getHoldbackQueue("id1").isEmpty());
	}

	@Ignore
	@Test
	public void shouldHoldFutureMessage() {
		Orderer causal = new CausalOrderer();
		VectorClock clock = new VectorClock();
		clock.increment("id1");
		clock.increment("id1");
		Message msg = new CausalMessage(new Message("id1", null), clock);

		causal.addMessage(msg);

		Assert.assertTrue(!causal.getHoldbackQueue("id1").isEmpty());
	}
	@Ignore
	@Test
	public void shouldNotHoldFutureMessageOnceReached() {
		Orderer causal = new CausalOrderer();
		VectorClock clock = new VectorClock();
		clock.increment("id1");
		clock.increment("id1");
		Message msg = new CausalMessage(new Message("id1", null), clock);

		causal.addMessage(msg);

		VectorClock clock2 = new VectorClock();
		clock2.increment("id1");
		Message msg2 = new CausalMessage( new Message("id1", null), clock2);

		causal.addMessage(msg2);

		Assert.assertTrue(causal.getHoldbackQueue("id1").isEmpty());
	}

	@Ignore
	@Test
	public void shouldNotHoldWithHigherOtherSeqNr() {
		Orderer causal = new CausalOrderer();

		VectorClock c0 = new VectorClock();
		c0.increment("id2");

		// Setup scenario
		VectorClock c1 = new VectorClock();
		c1.updateTime("id2", 2);

		VectorClock c2 = new VectorClock();
		c2.updateTime("id2", 3);

		Message m0 = new CausalMessage(new Message("id2", "m0"), c0);
		Message m1 = new CausalMessage(new Message("id2", "m1"), c1);
		Message m2 = new CausalMessage(new Message("id2", "m2"), c2);


		VectorClock clock = new VectorClock();
		clock.updateTime("id1", 2);
		clock.updateTime("id2", 3);
		Message msg = new CausalMessage(new Message("id1", "m4"), clock);

		VectorClock clock2 = new VectorClock();
		clock2.updateTime("id1",1);
		clock2.updateTime("id2", 3);

		Message msg2 = new CausalMessage(new Message("id1", "m4"), clock2);

		causal.addMessage(m0);
		causal.addMessage(m1);
		causal.addMessage(m2);
		causal.addMessage(msg);
		causal.addMessage(msg2);

		Assert.assertTrue(causal.getHoldbackQueue("id1").isEmpty());

	}

	@Ignore
	@Test
	public void shouldDeliverFirstEverMessage() {
		CausalOrderer causal = new CausalOrderer();

		DummyObserver dummy = new DummyObserver();

		causal.addObserver(dummy);

		VectorClock c1 = new VectorClock();
		c1.increment("id1");

		Message m1 = new CausalMessage(new Message("id1", "m1"), c1);

		causal.addMessage(m1);

		Assert.assertTrue(dummy.contains(m1));
	}

	@Ignore
	@Test
	public void shouldDeliverWithHigherOtherSeqNr() {
		CausalOrderer causal = new CausalOrderer();
		DummyObserver dummy = new DummyObserver();
		causal.addObserver(dummy);

		VectorClock c0 = new VectorClock();
		c0.increment("id2");

		// Setup scenario
		VectorClock c1 = new VectorClock();
		c1.updateTime("id2", 2);

		VectorClock c2 = new VectorClock();
		c2.updateTime("id2", 3);

		Message m0 = new CausalMessage(new Message("id2", "m0"), c0);
		Message m1 = new CausalMessage(new Message("id2", "m1"), c1);
		Message m2 = new CausalMessage(new Message("id2", "m2"), c2);


		VectorClock clock = new VectorClock();
		clock.updateTime("id1", 2);
		clock.updateTime("id2", 3);
		Message m3 = new CausalMessage(new Message("id1", "m4"), clock);

		VectorClock clock2 = new VectorClock();
		clock2.updateTime("id1",1);
		clock2.updateTime("id2", 3);

		Message m4 = new CausalMessage(new Message("id1", "m3"), clock2);

		causal.addMessage(m0);
		causal.addMessage(m1);
		causal.addMessage(m2);
		causal.addMessage(m3);
		causal.addMessage(m4);

		dummy.printMessages();

		Assert.assertTrue(dummy.containsAt(m0, 0) &&
				dummy.containsAt(m1, 1) &&
				dummy.containsAt(m2, 2) &&
				dummy.containsAt(m3, 3) &&
				dummy.containsAt(m4, 4));
	}

	@Ignore
	@Test
	public void shouldDeliverInEitherOrder() {
		CausalOrderer causal = new CausalOrderer();
		DummyObserver dummy = new DummyObserver();
		causal.addObserver(dummy);

		VectorClock c1 = new VectorClock();
		VectorClock c2 = new VectorClock();
		VectorClock c3 = new VectorClock();

		c1.increment("id1");

		c2.increment("id1");
		c2.increment("id2");

		c3.increment("id1");
		c3.increment("id3");

		Message m1 = new CausalMessage(new Message("id1", "m1"), c1);
		Message m2 = new CausalMessage(new Message("id2", "m2"), c2);
		Message m3 = new CausalMessage(new Message("id3", "m3"), c3);

		causal.addMessage(m2);
		causal.addMessage(m3);
		causal.addMessage(m1);

		Assert.assertTrue(dummy.containsAt(m1,0) &&
				((dummy.containsAt(m2,1) && dummy.containsAt(m3, 2)) || 
				 (dummy.containsAt(m3, 1) && dummy.containsAt(m2,2))));
	}


	@Test
	public void shouldPrepareMessage() {
		CausalOrderer causal = new CausalOrderer();

		DummyObserver dummy = new DummyObserver();

		causal.addObserver(dummy);

		Message m1 = causal.prepareMessage(new Message("id1", "m1"));

		causal.addMessage(m1);


		Assert.assertTrue(dummy.contains(m1));
	}
}

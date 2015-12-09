package se.umu.cs.dist.ht15.dali_ens15bsf;

import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;
import se.umu.cs.dist.ht15.dali_ens15bsf.ordering.FifoOrderer;
import se.umu.cs.dist.ht15.dali_ens15bsf.ordering.Orderer;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.time.VectorClock;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;


public class FifoTest {

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
/*
	@Test
	public void shouldReturnNullHoldbackAfterInit() {
		Orderer fifo = new FifoOrderer();
		Assert.assertTrue(fifo.getHoldbackQueue("id1") == null);
	}

	@Test(expected=NullPointerException.class)
	public void shouldThrowNullPtrExceptOnNullClock() {
		Orderer fifo = new FifoOrderer();
		Message msg = new FifoMessage(new Message("id1", null), null);
		fifo.addMessage(msg);
		
		Assert.assertTrue(fifo.getHoldbackQueue("id1") != null);
	}

	@Test
	public void shouldNotReturnNullHoldbackAfterMessage() {
		Orderer fifo = new FifoOrderer();
		VectorClock clock = new VectorClock();
		Message msg = new FifoMessage(new Message("id1", null), clock);
		fifo.addMessage(msg);
		
		Assert.assertTrue(fifo.getHoldbackQueue("id1") != null);
	}

	@Test
	public void shouldNotHoldFirstMessage() {
		Orderer fifo = new FifoOrderer();
		VectorClock clock = new VectorClock();

		Message msg = new FifoMessage(new Message("id1", null), clock);
		fifo.addMessage(msg);
		
		Assert.assertTrue(fifo.getHoldbackQueue("id1").isEmpty());
	}

	@Test
	public void shouldHoldSecondMessage() {
		Orderer fifo = new FifoOrderer();
		VectorClock clock = new VectorClock();
		VectorClock clock2 = new VectorClock();
		clock.updateTime("id1", 1);

		Message msg = new FifoMessage(new Message("id1", null), clock);
		fifo.addMessage(msg);
		
		Assert.assertTrue(!fifo.getHoldbackQueue("id1").isEmpty());
	}

	@Test
	public void shouldNotHoldTwoConsecutiveMessages() {
		Orderer fifo = new FifoOrderer();
		VectorClock clock = new VectorClock();
		clock.updateTime("id1", 1);

		Message msg = new FifoMessage(new Message("id1", null), clock);
		fifo.addMessage(msg);

		VectorClock clock2 = new VectorClock();
		Message msg2 = new FifoMessage(new Message("id1", null), clock2);
		fifo.addMessage(msg2);
		
		Assert.assertTrue(fifo.getHoldbackQueue("id1").isEmpty());
	}

	@Test
	public void shouldHoldLeapMessage() {
		Orderer fifo = new FifoOrderer();
		VectorClock leapClock = new VectorClock();
		leapClock.updateTime("id1", 4);

		Message leapMsg = new FifoMessage(new Message("id1", "content4"), leapClock);
		fifo.addMessage(leapMsg);

		VectorClock clock2 = new VectorClock();
		clock2.updateTime("id1", 1);

		Message msg2 = new FifoMessage(new Message("id1", "content2"), clock2);
		fifo.addMessage(msg2);

		VectorClock clock1 = new VectorClock();
		Message msg1 = new FifoMessage(new Message("id1", "content1"), clock1);
		fifo.addMessage(msg1);


		Assert.assertTrue(fifo.getHoldbackQueue("id1").size() == 1);
	}
	@Test
	public void shouldNotHoldConsecutiveMessages() {
		Orderer fifo = new FifoOrderer();
		VectorClock clock1 = new VectorClock();
		Message msg1 = new FifoMessage(new Message("id1", null), clock1);
		fifo.addMessage(msg1);

		VectorClock clock2 = new VectorClock();
		clock2.updateTime("id1", 1);

		Message msg2 = new FifoMessage(new Message("id1", null), clock2);
		fifo.addMessage(msg2);

		VectorClock clock3 = new VectorClock();
		clock3.updateTime("id1", 2);

		Message msg3 = new FifoMessage(new Message("id1", null), clock3);
		fifo.addMessage(msg3);

		Assert.assertTrue(fifo.getHoldbackQueue("id1").isEmpty());
	}

	@Test
	public void shouldDeliverFirstMessage() {
		FifoOrderer fifo = new FifoOrderer();
		VectorClock clock = new VectorClock();
		Message msg = new FifoMessage(new Message("id1", "content"), clock);
		DummyObserver dummy = new DummyObserver(fifo);
		fifo.addObserver(dummy);
		fifo.addMessage(msg);

		Assert.assertTrue(dummy.contains(msg));
	}

	@Test
	public void shouldDeliverTwoConsecutiveMessages() {
		FifoOrderer fifo = new FifoOrderer();

		DummyObserver dummy = new DummyObserver(fifo);
		fifo.addObserver(dummy);

		VectorClock clock = new VectorClock();
		clock.updateTime("id1", 1);

		Message msg = new FifoMessage(new Message("id1", null), clock);
		fifo.addMessage(msg);

		VectorClock clock2 = new VectorClock();
		Message msg2 = new FifoMessage(new Message("id1", null), clock2);
		fifo.addMessage(msg2);
		
		Assert.assertTrue(dummy.contains(msg) && dummy.contains(msg2));
	}

	@Test
	public void shouldDeliverWholeSequenceWhenMissingPartFilled() {
		FifoOrderer fifo = new FifoOrderer();
		DummyObserver dummy = new DummyObserver(fifo);
		fifo.addObserver(dummy);

		VectorClock clock1 = new VectorClock();

		VectorClock clock2 = new VectorClock();
		clock2.updateTime("id1", 1);

		VectorClock clock3 = new VectorClock();
		clock3.updateTime("id1", 2);

		VectorClock clock4 = new VectorClock();
		clock4.updateTime("id1", 3);


		Message msg1 = new FifoMessage(new Message("id1", "content1"), clock1);
		Message msg2 = new FifoMessage(new Message("id1", "content2"), clock2);
		Message msg3 = new FifoMessage(new Message("id1", "content3"), clock3);
		Message msg4 = new FifoMessage(new Message("id1", "content4"), clock4);

		fifo.addMessage(msg3);
		fifo.addMessage(msg4);
		fifo.addMessage(msg1);
		fifo.addMessage(msg2);


		Assert.assertTrue(	dummy.contains(msg1) && 
					dummy.contains(msg2) &&
					dummy.contains(msg3) &&
					dummy.contains(msg4));
	}


	@Ignore
	@Test
	public void shouldDeliverSequenceInFifoOrder() {
		FifoOrderer fifo = new FifoOrderer();
		DummyObserver dummy = new DummyObserver(fifo);
		fifo.addObserver(dummy);

		VectorClock clock1 = new VectorClock();

		VectorClock clock2 = new VectorClock();
		clock2.updateTime("id1", 1);

		VectorClock clock3 = new VectorClock();
		clock3.updateTime("id1", 2);

		VectorClock clock4 = new VectorClock();
		clock4.updateTime("id1", 3);


		Message msg1 = new FifoMessage(new Message("id1", "content1"), clock1);
		Message msg2 = new FifoMessage(new Message("id1", "content2"), clock2);
		Message msg3 = new FifoMessage(new Message("id1", "content3"), clock3);
		Message msg4 = new FifoMessage(new Message("id1", "content4"), clock4);

		fifo.addMessage(msg3);
		fifo.addMessage(msg4);
		fifo.addMessage(msg1);
		fifo.addMessage(msg2);


		Assert.assertTrue(	dummy.containsAt(msg1,0) && 
					dummy.containsAt(msg2, 1) &&
					dummy.containsAt(msg3, 2) &&
					dummy.containsAt(msg4, 3));
	}


	@Ignore
	@Test
	public void shouldPrepareMessageWithClock() {
		 TODO What should happen if same time stamp on two messages? 
		FifoOrderer fifo = new FifoOrderer();

		Message m = fifo.prepareMessage(new Message("id1", "m1"));
		fifo.addMessage(m);

		Assert.assertTrue(fifo.getHoldbackQueue("id1").isEmpty());
	}
*/
}

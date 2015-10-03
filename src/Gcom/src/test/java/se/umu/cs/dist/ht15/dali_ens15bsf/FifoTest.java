package se.umu.cs.dist.ht15.dali_ens15bsf;

import org.junit.Test;
import junit.framework.Assert;
import se.umu.cs.dist.ht15.dali_ens15bsf.time.VectorClock;
import se.umu.cs.dist.ht15.dali_ens15bsf.ordering.Orderer;
import se.umu.cs.dist.ht15.dali_ens15bsf.ordering.FifoOrderer;
import java.util.Observer;
import java.util.Observable;
import java.util.Collection;
import java.util.LinkedList;


public class FifoTest {

	private class DummyObserver implements Observer {
		private Observable observable;
		private Collection<Message> messages;

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
	}


	@Test
	public void shouldReturnNullHoldbackAfterInit() {
		Orderer fifo = new FifoOrderer();
		Assert.assertTrue(fifo.getHoldbackQueue("id1") == null);
	}

	@Test(expected=NullPointerException.class)
	public void shouldThrowNullPtrExceptOnNullClock() {
		Orderer fifo = new FifoOrderer();
		Message msg = new Message("id1", null, null);
		fifo.addMessage(msg);
		
		Assert.assertTrue(fifo.getHoldbackQueue("id1") != null);
	}

	@Test
	public void shouldNotReturnNullHoldbackAfterMessage() {
		Orderer fifo = new FifoOrderer();
		VectorClock clock = new VectorClock();
		Message msg = new Message("id1", null, clock);
		fifo.addMessage(msg);
		
		Assert.assertTrue(fifo.getHoldbackQueue("id1") != null);
	}


//	@Test
//	public void shouldReturnNonEmptyHoldbackAfterMessage() {
//		Orderer fifo = new FifoOrderer();
//		Message msg = new Message("id1", null, null);
//		fifo.addMessage(msg);
//		
//		Assert.assertTrue(!fifo.getHoldbackQueue("id1").isEmpty());
//	}

	@Test
	public void shouldNotHoldFirstMessage() {
		Orderer fifo = new FifoOrderer();
		VectorClock clock = new VectorClock();
		//clock.increment("id1");
		Message msg = new Message("id1", null, clock);
		fifo.addMessage(msg);
		
		Assert.assertTrue(fifo.getHoldbackQueue("id1").isEmpty());
	}

	@Test
	public void shouldHoldSecondMessage() {
		Orderer fifo = new FifoOrderer();
		VectorClock clock = new VectorClock();
		VectorClock clock2 = new VectorClock();
		clock.updateTime("id1", 1);

		Message msg = new Message("id1", null, clock);
		fifo.addMessage(msg);
		
		Assert.assertTrue(!fifo.getHoldbackQueue("id1").isEmpty());
	}

	@Test
	public void shouldNotHoldTwoConsecutiveMessages() {
		Orderer fifo = new FifoOrderer();
		VectorClock clock = new VectorClock();
		clock.updateTime("id1", 1);

		Message msg = new Message("id1", null, clock);
		fifo.addMessage(msg);

		VectorClock clock2 = new VectorClock();
		Message msg2 = new Message("id1", null, clock2);
		fifo.addMessage(msg2);
		
		Assert.assertTrue(fifo.getHoldbackQueue("id1").isEmpty());
	}

	@Test
	public void shouldHoldLeapMessage() {
		Orderer fifo = new FifoOrderer();
		VectorClock leapClock = new VectorClock();
		leapClock.updateTime("id1", 4);

		Message leapMsg = new Message("id1", "content4", leapClock);
		fifo.addMessage(leapMsg);

		System.err.println("SIZE: "+fifo.getHoldbackQueue("id1").size());
		VectorClock clock2 = new VectorClock();
		clock2.updateTime("id1", 1);

		Message msg2 = new Message("id1", "content2", clock2);
		fifo.addMessage(msg2);

		System.err.println("SIZE: "+fifo.getHoldbackQueue("id1").size());
		VectorClock clock1 = new VectorClock();
		Message msg1 = new Message("id1", "content1", clock1);
		fifo.addMessage(msg1);

		System.err.println("SIZE: "+fifo.getHoldbackQueue("id1").size());

		for( Object o : fifo.getHoldbackQueue("id1")) {
			Message m = (Message)o;
			System.out.println("Message: ID = " + m.getId() + ", Content = " + m.getContent());
		}

		Assert.assertTrue(fifo.getHoldbackQueue("id1").size() == 1);
	}
	@Test
	public void shouldNotHoldConsecutiveMessages() {
		Orderer fifo = new FifoOrderer();
		VectorClock clock1 = new VectorClock();
		Message msg1 = new Message("id1", null, clock1);
		fifo.addMessage(msg1);

		VectorClock clock2 = new VectorClock();
		clock2.updateTime("id1", 1);

		Message msg2 = new Message("id1", null, clock2);
		fifo.addMessage(msg2);

		VectorClock clock3 = new VectorClock();
		clock3.updateTime("id1", 2);

		Message msg3 = new Message("id1", null, clock3);
		fifo.addMessage(msg3);

		Assert.assertTrue(fifo.getHoldbackQueue("id1").isEmpty());
	}

	@Test
	public void shouldDeliverFirstMessage() {
		FifoOrderer fifo = new FifoOrderer();
		VectorClock clock = new VectorClock();
		Message msg = new Message("id1", "content", clock);
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

		Message msg = new Message("id1", null, clock);
		fifo.addMessage(msg);

		VectorClock clock2 = new VectorClock();
		Message msg2 = new Message("id1", null, clock2);
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


		Message msg1 = new Message("id1", "content1", clock1);
		Message msg2 = new Message("id1", "content2", clock2);
		Message msg3 = new Message("id1", "content3", clock3);
		Message msg4 = new Message("id1", "content4", clock4);

		fifo.addMessage(msg3);
		fifo.addMessage(msg4);
		fifo.addMessage(msg1);
		fifo.addMessage(msg2);


		Assert.assertTrue(	dummy.contains(msg1) && 
					dummy.contains(msg2) &&
					dummy.contains(msg3) &&
					dummy.contains(msg4));
	}


	@Test
	public void should() {
		/* TODO What should happen if same time stamp on two messages? */
	}

}

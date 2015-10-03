package se.umu.cs.dist.ht15.dali_ens15bsf;

import org.junit.Test;
import junit.framework.Assert;
import se.umu.cs.dist.ht15.dali_ens15bsf.time.VectorClock;
import se.umu.cs.dist.ht15.dali_ens15bsf.ordering.Orderer;
import se.umu.cs.dist.ht15.dali_ens15bsf.ordering.FifoOrderer;

public class FifoTest {
	@Test
	public void shouldReturnNullHoldbackAfterInit() {
		Orderer fifo = new FifoOrderer();
		Assert.assertTrue(fifo.getHoldbackQueue("id1") == null);
	}


	@Test
	public void shouldNotReturnNullHoldbackAfterMessage() {
		Orderer fifo = new FifoOrderer();
		Message msg = new Message("id1", null, null);
		fifo.addMessage(msg);
		
		Assert.assertTrue(fifo.getHoldbackQueue("id1") != null);
	}

	@Test
	public void shouldReturnNonEmptyHoldbackAfterMessage() {
		Orderer fifo = new FifoOrderer();
		Message msg = new Message("id1", null, null);
		fifo.addMessage(msg);
		
		Assert.assertTrue(!fifo.getHoldbackQueue("id1").isEmpty());
	}


	@Test
	public void shouldEmptyHoldbackOnTwoConsecutiveMsg() {
		Orderer fifo = new FifoOrderer();
		VectorClock clock = new VectorClock();
		clock.increment("id1");
		Message msg = new Message("id1", null, clock);
		fifo.addMessage(msg);

	}
}

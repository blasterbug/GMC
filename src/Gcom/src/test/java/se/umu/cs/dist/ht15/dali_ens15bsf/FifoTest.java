package se.umu.cs.dist.ht15.dali_ens15bsf;

import org.junit.Test;
import junit.framework.Assert;
import se.umu.cs.dist.ht15.dali_ens15bsf.time.VectorClock;
import se.umu.cs.dist.ht15.dali_ens15bsf.ordering.Orderer;
import se.umu.cs.dist.ht15.dali_ens15bsf.ordering.FifoOrderer;

public class FifoTest {
	@Test
	public void shouldReturnNullQueueAfterInit() {
		Orderer fifo = new FifoOrderer();
		Assert.assertTrue(fifo.getOrdering("id1") == null);
	}


	@Test
	public void shouldNotReturnNullQueueAfterMessage() {
		Orderer fifo = new FifoOrderer();
		Message msg = new Message("id1", null, null);
		fifo.addMessageToOrder(msg);
		
		Assert.assertTrue(fifo.getOrdering("id1") != null);
	}

	@Test
	public void shouldReturnNonEmptyQueueAfterMessage() {
		Orderer fifo = new FifoOrderer();
		Message msg = new Message("id1", null, null);
		fifo.addMessageToOrder(msg);
		
		Assert.assertTrue(!fifo.getOrdering("id1").isEmpty());
	}

}

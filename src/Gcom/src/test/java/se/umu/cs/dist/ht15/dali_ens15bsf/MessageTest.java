package se.umu.cs.dist.ht15.dali_ens15bsf;

import junit.framework.Assert;
import org.junit.Test;
import se.umu.cs.dist.ht15.dali_ens15bsf.time.VectorClock;

public class MessageTest {
	@Test
	public void shouldConstructId() {
		VectorClock clock = new VectorClock();
		Message msg = new Message("id1", "content", clock);
		Assert.assertTrue(msg.getId().equals("id1"));
	}

	@Test
	public void shouldConstructContent() {
		VectorClock clock = new VectorClock();
		Message msg = new Message("id1", "content", clock);
		Assert.assertTrue(msg.getContent().equals("content"));
	}

	@Test
	public void shouldConstructClock() {
		VectorClock clock = new VectorClock();
		Message msg = new Message("id1", "content", clock);
		Assert.assertTrue(msg.getClock().equals(clock));
	}


	@Test
	public void shouldEqualsOnSameIdMsgAndClock() {
		VectorClock clock1 = new VectorClock();
		Message msg1 = new Message("id1", "content", clock1);
		VectorClock clock2 = new VectorClock();
		Message msg2 = new Message("id1", "content", clock2);

		Assert.assertTrue(msg1.equals(msg2));

	}

}

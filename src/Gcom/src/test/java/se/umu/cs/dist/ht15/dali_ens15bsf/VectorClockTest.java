package se.umu.cs.dist.ht15.dali_ens15bsf;

import junit.framework.Assert;
import org.junit.Test;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.time.VectorClock;

public class VectorClockTest {
/*
	@Test
	public void shouldInitializeToZero(){
		VectorClock clock = new VectorClock();
		Integer i = new Integer(0);
		Assert.assertTrue(i.equals(clock.get("id1")));

	}

	@Test
	public void shouldReturnOneWhenNotAdded() {
		VectorClock clock = new VectorClock();
		clock.increment("id1");
		Integer i = new Integer(1);
		Assert.assertTrue(i.equals(clock.get("id1")));
	}

	@Test
	public void shouldIncrementByOne() {
		VectorClock clock = new VectorClock();
		clock.increment("id1");
		clock.increment("id1");
		Integer i = new Integer(2);
		Assert.assertTrue(i.equals(clock.get("id1")));
	}

	@Test
	public void shouldCreateEqualOnInit() {
		VectorClock c1 = new VectorClock();
		VectorClock c2 = new VectorClock();
		Assert.assertTrue(c1.compare(c2)==0);
	}

	@Test
	public void shouldBeLargerOnIncrement() {
		VectorClock c1 = new VectorClock();
		VectorClock c2 = new VectorClock();
		c1.increment("id1");
		Assert.assertTrue(c1.compare(c2) == 1 );

	}

	@Test
	public void shouldBeSmallerOnIncrement() {
		VectorClock c1 = new VectorClock();
		VectorClock c2 = new VectorClock();
		c2.increment("id1");
		Assert.assertTrue(c1.compare(c2)<0);

	}

	@Test
	public void shouldBeEqualOnIncrements() {
		VectorClock c1 = new VectorClock();
		VectorClock c2 = new VectorClock();
		c2.increment("id1");
		c1.increment("id1");
		Assert.assertTrue(c1.compare(c2) == 0);
	}

	@Test
	public void shouldBeLessOnIncrements() {
		VectorClock c1 = new VectorClock();
		VectorClock c2 = new VectorClock();
		c2.increment("id1");
		c2.increment("id2");
		c1.increment("id1");
		Assert.assertTrue(c1.compare(c2) < 0);
	}

	@Test
	public void shouldBeConcurrentOnTwoDifferentKeys() {
		VectorClock c1 = new VectorClock();
		VectorClock c2 = new VectorClock();
		c2.increment("id2");
		c1.increment("id1");
		Assert.assertTrue(c1.compare(c2) == 2);
	}
	*/
}

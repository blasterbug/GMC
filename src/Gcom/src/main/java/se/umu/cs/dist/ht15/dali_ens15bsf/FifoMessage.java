package se.umu.cs.dist.ht15.dali_ens15bsf;

import se.umu.cs.dist.ht15.dali_ens15bsf.com.time.VectorClock;

public class FifoMessage extends MessageDecorator {
	private VectorClock clock;

	public FifoMessage(Message msg, VectorClock clock) {
		super(msg);
		this.clock = clock;
	}

	public VectorClock getClock() {
		return this.clock;
	}

	public boolean equals(Message m) {
		VectorClock c = ((FifoMessage)m).getClock();
		return super.equals(m) && 
			c.equals(this.clock);
	}
}

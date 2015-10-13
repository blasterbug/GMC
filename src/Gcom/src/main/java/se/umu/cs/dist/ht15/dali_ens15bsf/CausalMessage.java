package se.umu.cs.dist.ht15.dali_ens15bsf;

import se.umu.cs.dist.ht15.dali_ens15bsf.time.VectorClock;

public class CausalMessage extends MessageDecorator {
	private VectorClock clock;

	public CausalMessage(Message msg, VectorClock clock) {
		super(msg);
		this.clock = clock;
	}

	public VectorClock getClock() {
		return this.clock;
	}

	public boolean equals(Message m) {
		VectorClock c = ((CausalMessage)m).getClock();
		return super.equals(m) && 
			c.equals(this.clock);
	}
}
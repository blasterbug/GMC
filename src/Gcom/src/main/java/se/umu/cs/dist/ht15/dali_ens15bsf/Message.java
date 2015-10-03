package se.umu.cs.dist.ht15.dali_ens15bsf;

import se.umu.cs.dist.ht15.dali_ens15bsf.time.VectorClock;

public class Message {
	private String id;
	private String content;
	private VectorClock clock;

	public Message() {

	}

	public Message(String id, String content, VectorClock clock) {
		this.id = id;
		this.content = content;
		this.clock = clock;


	}

	public String getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public VectorClock getClock() {
		return clock;
	}

	public boolean equals(Message m) {
		return m.getId().equals(id) &&
			m.getContent().equals(content) &&
			m.getClock().equals(clock);
	}
}

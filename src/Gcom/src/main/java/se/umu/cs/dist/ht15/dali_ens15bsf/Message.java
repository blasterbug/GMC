package se.umu.cs.dist.ht15.dali_ens15bsf;

public class Message {
	private String id;
	private String content;

	public Message() {

	}

	public Message(String id, String content) {
		this.id = id;
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public boolean equals(Message m) {
		return m.getId().equals(id) &&
			m.getContent().equals(content);
	}
}

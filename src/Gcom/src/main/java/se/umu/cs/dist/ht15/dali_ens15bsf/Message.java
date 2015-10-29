package se.umu.cs.dist.ht15.dali_ens15bsf;

import java.io.Serializable;

public class Message implements Serializable
{
	private static final long serialVersionUID = 1665727741730526512L;
	private String id;
	private String content;

	public Message() {}

	public Message(Message m)  {
		this.id = m.getId();
		this.content = m.getContent();

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

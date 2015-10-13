package se.umu.cs.dist.ht15.dali_ens15bsf;

public abstract class MessageDecorator extends Message {
	protected Message decoratedMessage;

	public MessageDecorator(Message msg) {
		decoratedMessage = msg;
	}

	@Override
	public String getId() {
		return decoratedMessage.getId();
	}

	@Override
	public String getContent() {
		return decoratedMessage.getContent();
	}

	public boolean equals(Message m) {
		return super.equals(m);
	}

}

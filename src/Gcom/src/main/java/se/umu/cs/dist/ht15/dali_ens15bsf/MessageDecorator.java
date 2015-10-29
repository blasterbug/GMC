package se.umu.cs.dist.ht15.dali_ens15bsf;

import java.io.Serializable;

public abstract class MessageDecorator<T extends Serializable> extends Message<T> {
	protected Message<T > decoratedMessage;

	public MessageDecorator(Message msg) {
		decoratedMessage = msg;
	}

	@Override
	public String getId() {
		return decoratedMessage.getId();
	}

	@Override
	public T getContent() {
		return decoratedMessage.getContent();
	}

	public boolean equals(Message m) {
		return super.equals(m);
	}

}

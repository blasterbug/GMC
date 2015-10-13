package se.umu.cs.dist.ht15.dali_ens15bsf.ordering;

import java.util.Queue;
import java.util.Observable;
import se.umu.cs.dist.ht15.dali_ens15bsf.Message;

public abstract class Orderer extends Observable {
	public abstract void addMessage(Message msg);
	public abstract Message prepareMessage(Message msg);
	public abstract Queue getHoldbackQueue(String id);
}

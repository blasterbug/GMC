package se.umu.cs.dist.ht15.dali_ens15bsf.ordering;

import java.util.Queue;
import se.umu.cs.dist.ht15.dali_ens15bsf.Message;

public interface Orderer {
	public void addMessage(Message msg);
	public Queue getHoldbackQueue(String id);
}

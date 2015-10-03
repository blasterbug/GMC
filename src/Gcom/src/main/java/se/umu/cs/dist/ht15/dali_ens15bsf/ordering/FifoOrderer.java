package se.umu.cs.dist.ht15.dali_ens15bsf.ordering;

import se.umu.cs.dist.ht15.dali_ens15bsf.Message;
import java.util.Queue;

public class FifoOrderer implements Orderer {


	@Override
	public void addMessageToOrder(Message msg) {

	}

	@Override
	public Queue getOrdering() {
		return null;
	}
}

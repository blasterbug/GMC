package se.umu.cs.dist.ht15.dali_ens15bsf.ordering;

import se.umu.cs.dist.ht15.dali_ens15bsf.Message;
import se.umu.cs.dist.ht15.dali_ens15bsf.time.VectorClock;

import java.util.Observable;
import java.util.Queue; 

public class CausalOrderer extends Observable implements Orderer {

	@Override
	public void addMessage(Message msg) {

	}

	@Override
	public Queue getHoldbackQueue(String id) {
		return null;
	}

}

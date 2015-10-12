package se.umu.cs.dist.ht15.dali_ens15bsf.groupmanagement;

import se.umu.cs.dist.ht15.dali_ens15bsf.Message;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.MemberRemote;

public interface Member {
	public void join(MemberRemote m, String id);
	public void sendMessage(Message m);
	public void receiveMessage(Message m);
}

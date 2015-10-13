package se.umu.cs.dist.ht15.dali_ens15bsf.groupmanagement;

import se.umu.cs.dist.ht15.dali_ens15bsf.Message;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.RemoteMember;

import java.util.Collection;

public interface Member {
	public void join(RemoteMember m, String id);
	public void sendMessage(Message m);
	public void receiveMessage(Message m);
	public Collection<RemoteMember> getView();
	public RemoteMember getRemoteMember();
	public void setId(String id);
}

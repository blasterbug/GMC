package se.umu.cs.dist.ht15.dali_ens15bsf.groupmanagement;

import se.umu.cs.dist.ht15.dali_ens15bsf.Message;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.RemoteMember;
import se.umu.cs.dist.ht15.dali_ens15bsf.nameserver.NamingServiceUnavailableException;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Collection;

public interface Member<T extends Serializable> {
	public void join(RemoteMember m, String id);
	public void sendMessage(T content);
	public void receiveMessage(Message m);
	public Collection<RemoteMember> getView();
	public RemoteMember getRemoteMember();
	public void setId(String id);
	public void connectToNameserver() throws RemoteException, NamingServiceUnavailableException;
	public void joinGroup(String gid) throws RemoteException;
	public void leaveGroup(String gid) throws RemoteException;
	public void updateIdFromNameServer() throws RemoteException;
	public String getId();
	public RemoteMember getLeader();
	public void addToView(RemoteMember m, String id);
	public void removeFromView(RemoteMember m, String id);

}

package se.umu.cs.dist.ht15.dali_ens15bsf.groupmanagement;

import se.umu.cs.dist.ht15.dali_ens15bsf.Message;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.RemoteMember;
import se.umu.cs.dist.ht15.dali_ens15bsf.nameserver.NamingServiceUnavailableException;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Observable;

public abstract class Member<T extends Serializable> extends Observable
{
	public abstract void join(RemoteMember m, String id);
	public abstract void sendMessage(T content);
	public abstract void receiveMessage(Message m);
	public abstract Collection<RemoteMember> getView();
	public abstract RemoteMember getRemoteMember();
	public abstract void setId(String id);
	public abstract void connectToNameserver() throws RemoteException, NamingServiceUnavailableException;
	public abstract void joinGroup(String gid) throws RemoteException;
	public abstract void leaveGroup(String gid) throws RemoteException;
	public abstract void updateIdFromNameServer() throws RemoteException;
	public abstract String getId();
	public abstract RemoteMember getLeader();
	public abstract void addToView(RemoteMember m, String id);
	public abstract void removeFromView(RemoteMember m, String id);

}

package se.umu.cs.dist.ht15.dali_ens15bsf.groupmanagement;

import se.umu.cs.dist.ht15.dali_ens15bsf.Message;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.*;
import se.umu.cs.dist.ht15.dali_ens15bsf.ordering.Orderer;
import se.umu.cs.dist.ht15.dali_ens15bsf.nameserver.*;

import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import java.util.Map;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Collection;
import java.util.ArrayList;

public class MemberImpl implements Member, ComObserver, Observer
{
//	private Map<String, RemoteMember> view;
	private Collection<RemoteMember> view;
	private Orderer orderer;
	private CommMember self;
	private CommMember leader;
	private String id;
	private NamingServiceRemote nameserver;

	public MemberImpl(Orderer o, MulticastStrategy strg) throws RemoteException{
//		view = new HashMap<String, RemoteMember>();
		view = new ArrayList<RemoteMember>();
		orderer = o;

		self = (RemoteMember)UnicastRemoteObject.exportObject(new CommMember( strg ),0);
		System.out.println("HERE'S JOHNNY");
		System.out.println(self);	
		self.addObserver( this );
		orderer.addObserver( this );
	}

	@Override
	public void connectToNameserver() throws RemoteException, NotBoundException {
		Registry dictionary = LocateRegistry.getRegistry(NamingService.SERVER_PORT);
		nameserver = (NamingServiceRemote) dictionary.lookup(NamingService.SERVICE_NAME);
	}

	@Override
	public void updateIdFromNameServer() throws RemoteException {
		this.id = nameserver.getMyId(self);
	}

	@Override
	public void joinGroup(String gid) throws RemoteException{
		leader = (CommMember)nameserver.joinGroup(gid, self);
	}

	@Override
	public RemoteMember getLeader() {
		return this.leader;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public RemoteMember getRemoteMember()
	{
		return self;
	}


	@Override
	public void join(RemoteMember m, String id) {
		System.out.println("Remote member ["+id+"] is joining");	
		view.add(m);
		for (RemoteMember rm : view){
			try {
				rm.join(m, id);
			}catch(RemoteException e) {

			}
		}
	}

	/**
	  * @param m A message to send to the group
	  */
	@Override
	public void sendMessage(Message m) {
		/* Prepare according to orderer */
		Message preparedMessage = orderer.prepareMessage(m);
		
		/* Create communication message */
		CommMessage<Message> msg;
		msg = new CommMessage(preparedMessage);

		/* Send message to view */
		try {
			self.post(msg, view);
		} catch(UnreachableRemoteObjectException exp) {
			System.out.println("BINOG");	
		}
	}

	@Override
	public void receiveMessage(Message m) {
		System.out.println("Bngko");	
		orderer.addMessage(m);
	}

	@Override
	public Collection<RemoteMember> getView() {
		return view;
	}

	@Override
	public void update(Observable obs, Object o) {

		Message m = (Message)o;
		System.out.println("Member ["+this.id+"] recevied MESSAGE with content ["+m.getContent()+"] received from ["+m.getId()+"]");	
	}

	/**
	 * Notify Observers when a new incoming message arrive
	 *
	 * @param msg message to give to the Observer
	 */
	@Override
	public void notifyObservers ( CommMessage msg )
	{
		// pretty awful
		receiveMessage( ((CommMessage<Message>)msg).getContent() );
	}

	/**
	 * Notify observer when new member want to join a group
	 *
	 * @param member  New member joining to the group
	 * @param groupID Group name to join
	 */
	@Override
	public void notifyNewMember ( RemoteMember member, String groupID )
	{
		System.out.println("Here we go");	
		join( member, groupID );
	}
}

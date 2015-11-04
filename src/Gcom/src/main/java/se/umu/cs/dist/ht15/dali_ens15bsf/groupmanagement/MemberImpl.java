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

public class MemberImpl extends Observable implements Member, ComObserver, Observer
{
	private Map<String, RemoteMember> view;
//	private Collection<RemoteMember> view;
	private Orderer orderer;
	private ComMember self;
	private RemoteMember leader;
	private String id;
	private NamingServiceRemote nameserver;
	private Map<String, RemoteMember> groups;
	private String groupId; // Will be superseded by above line once full support is implemented

	public MemberImpl(Orderer o, MulticastStrategy strg) throws RemoteException{
		view = new HashMap<String, RemoteMember>();
//		view = new ArrayList<RemoteMember>();
		groups = new HashMap<String, RemoteMember>();
		orderer = o;

		self = new ComMember(strg, this);
		UnicastRemoteObject.exportObject(self, 0);
		//System.out.println(self);
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
		this.self.setId(this.id);
		this.view.put(id,self);
	}


	@Override
	public void joinGroup(String gid) throws RemoteException{
		leader = nameserver.joinGroup(gid, self);
		this.groups.put( gid, leader );
		this.groupId = gid;
	}

	@Override
	public RemoteMember getLeader() {
		return this.leader;
	}

	@Override
	public void setId(String id) {
		this.id = id;
		this.self.setId(id);
		this.view.put(id,self);
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
	public synchronized void join(RemoteMember m, String id) {
//		System.out.println("Remote member ["+id+"] is joining "+this.id);
		if(view.keySet().contains(id) && view.get(id).equals(m))
			return;

		addToView(m,id);
		for (String key : view.keySet()){
			try {
				RemoteMember rm = view.get(key);
				if(!(key.equals(this.id))) {
//					System.out.println("Joining "+rm.getId() + " in "+this.id);
					rm.addToView(m,id);
				}
				if (!key.equals(id))
					m.addToView(rm,key);
			} catch (RemoteException e) {
				System.out.println("Failed to join: " + e.getMessage());
			}
		}
//		System.out.println("Putting ["+id+"] to "+this.id);	
	}

	@Override
	public synchronized void addToView(RemoteMember m, String id) {
//		System.out.println("Adding ["+id+"] to view in "+this.id);	
		view.put(id, m);
	}

	/**
	  * @param m A message to send to the group
	  */
	@Override
	public void sendMessage(Message m) {
		/* Prepare according to orderer */
		Message preparedMessage = orderer.prepareMessage(m);
		
		/* Create communication message */
		ComMessage<Message> msg;
		msg = new ComMessage(preparedMessage);

		/*
		System.out.println("Current view");	
		for ( String rm : view.keySet()) {
			System.out.print(rm+" ");
		}
		System.out.println();	
		*/
			 
		/* Send message to view */
		try {
			self.post(msg, view.values());
		} catch(UnreachableRemoteObjectException exp) {
	//		exp.printStackTrace();
			try {
				for ( RemoteMember rm : self.getUnreachableRemoteObjects() ) 
					handleUnavailableMember(rm);
			} catch (RemoteException ex) {
				System.out.println(ex.getMessage());	
			}

		}
		
//		System.out.println("BINOG");	
	}

	@Override
	public void receiveMessage(Message m) {
//		System.out.println("Bngko");	
		orderer.addMessage( m );
	}

	@Override
	public Collection<RemoteMember> getView() {
		return view.values();
	}

	@Override
	public void update(Observable obs, Object o) {

		Message m = (Message)o;
		super.setChanged();
		super.notifyObservers(m);
		System.out.println("Member ["+this.id+"] received MESSAGE with content ["+m.getContent()+"] received from ["+m.getId()+"]");

	}

	/**
	 * Notify Observers when a new incoming message arrive
	 *
	 * @param msg message to give to the Observer
	 */
	@Override
	public void notifyObservers ( ComMessage msg )
	{
		// pretty awful
		receiveMessage( ((ComMessage<Message>)msg).getContent() );
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
//		System.out.println("Here we go");	
		join( member, groupID );
	}

	@Override
	public void notifyNewLeader ( RemoteMember newLeader, String groupId) {
		System.out.println("Setting new leader");	
		this.groups.put( groupId, newLeader );
	}

	@Override
	public void notifyAddToView(RemoteMember m, String id) {
		this.addToView( m, id );
	}

	private void handleUnavailableMember(RemoteMember member) throws RemoteException {
		System.out.println("Trying to update leader");	
		RemoteMember lead = groups.get(this.groupId);

		if ( lead != null && member.equals(lead)) {
			nameserver.updateLeader(this.groupId,this.getRemoteMember());
			for ( RemoteMember rm : view.values()) {
				rm.updateLeader(lead, this.groupId);
			}
		}
		view.remove( member.getId() );
	}
}

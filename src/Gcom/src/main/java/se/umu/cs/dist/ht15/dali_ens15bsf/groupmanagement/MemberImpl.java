package se.umu.cs.dist.ht15.dali_ens15bsf.groupmanagement;

import se.umu.cs.dist.ht15.dali_ens15bsf.Message;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.*;
import se.umu.cs.dist.ht15.dali_ens15bsf.nameserver.NamingServerFabric;
import se.umu.cs.dist.ht15.dali_ens15bsf.nameserver.NamingServiceRemote;
import se.umu.cs.dist.ht15.dali_ens15bsf.nameserver.NamingServiceUnavailableException;
import se.umu.cs.dist.ht15.dali_ens15bsf.ordering.Orderer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

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
	public void connectToNameserver() throws RemoteException, NamingServiceUnavailableException {
		nameserver = NamingServerFabric.NamingService();
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

	@Override
	public void removeFromView(RemoteMember m, String id) {
		view.remove(id);
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
				Collection<RemoteMember> tmp = self.getUnreachableRemoteObjects();
				System.out.println("Handling unreachables " + tmp.size());	
				for ( RemoteMember rm : tmp ) {
					System.out.println("Handling " +rm);	
					handleUnavailableMember(rm);
				}
			} catch (RemoteException ex) {
				System.out.println("ERROR " + ex.getMessage());	
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

	@Override
	public void notifyRemoveFromView(RemoteMember m, String id) {
		this.removeFromView(m, id);
	}

	private void handleUnavailableMember(RemoteMember member) throws RemoteException {
		RemoteMember lead = groups.get(this.groupId);

		//view.remove(member.getId());
		String idToRemove = "";
		for ( String id : view.keySet() ) {
			if (view.get(id).equals(member)) {
				idToRemove = id;
				view.remove(id);
				break;
			}
		}
		if ( lead != null && member.equals(lead)) {
			nameserver.updateLeader(this.groupId,this.getRemoteMember());
			for ( RemoteMember rm : view.values()) {
				rm.updateLeader(lead, this.groupId);
			}
		}

		for ( RemoteMember rm : view.values() ) 
			rm.removeFromView(member, idToRemove);
	}
}

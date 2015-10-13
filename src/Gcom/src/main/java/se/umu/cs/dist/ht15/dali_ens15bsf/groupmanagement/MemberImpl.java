package se.umu.cs.dist.ht15.dali_ens15bsf.groupmanagement;

import se.umu.cs.dist.ht15.dali_ens15bsf.Message;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.*;
import se.umu.cs.dist.ht15.dali_ens15bsf.ordering.Orderer;

import java.rmi.RemoteException;

import java.util.Map;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Collection;

public class MemberImpl implements Member, ComObserver
{
	private Map<String, RemoteMember> view;
	private Orderer orderer;
	private CommMember self;

	public MemberImpl(Orderer o, MulticastStrategy strg) {
		view = new HashMap<String, RemoteMember>();
		orderer = o;

		self = new CommMember( strg );
		self.addObserver( this );
	}

	@Override
	public RemoteMember getRemoteMember()
	{
		return self;
	}


	@Override
	public void join(RemoteMember m, String id) {
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
		CommMessage<Message> msg;
		msg = new CommMessage(preparedMessage);

		/* Send message to view */
		try {
			self.post(msg, view.values());
		} catch(RemoteException exp) {
			// TODO 
		}
	}

	@Override
	public void receiveMessage(Message m) {
		System.out.println("MESSAGE with content ["+m.getContent()+"] received from ["+m.getId()+"]");	
	}

	@Override
	public Collection<RemoteMember> getView() {
		return view.values();
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
		join( member, groupID );
	}
}

package se.umu.cs.dist.ht15.dali_ens15bsf.com;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by ens15bsf on 2015-10-08.
 * Define the remote object used to manipulate members in Gcom
 */
public class ComMember extends ComObservable implements RemoteMember, Serializable
{
  private static final long serialVersionUID = 4672654439762386594L;
  protected ArrayList<RemoteMember> group;
  //protected Member owner;
  protected MulticastStrategy multicastStrategy;
  private String id;


  /**
   * Create a new node
   *
   * @param strategy Strategy to use for multicasting messages
   */
  public ComMember ( MulticastStrategy strategy, ComObserver mbr )
  {
	  super();
    group = new ArrayList<RemoteMember>();
    multicastStrategy = strategy;
    multicastStrategy.setOwner( this );
    super.addObserver( mbr );
    this.id = null;
  }

  /*public void setOwner(Member m) {
	  this.owner = m;
  }*/

  /**
   * Send a message by multicast to the other nodes
   *
   * @param msg   Message to multicast
   * @param group Group to send the message
   */
  public void post ( ComMessage msg, Collection<RemoteMember> group ) throws UnreachableRemoteObjectException
  {
    Collection<RemoteMember> temp = new ArrayList<RemoteMember>();
    try {
    for (RemoteMember m : group)
	    if (!m.getId().equals(this.id)) {
		    temp.add(m);
	    }
    } catch (RemoteException e) {
	    System.out.println(e.getMessage()); 	
    }
    multicastStrategy.send( msg, group );
  }

  /**
   * Receive a Communication message from another node
   *
   * @param msg Message to receive
   * @throws java.rmi.RemoteException
   */
  @Override
  public void deliver ( ComMessage msg ) throws RemoteException
  {
    //multicastStrategy.receive( msg );
    //owner.receiveMessage( (Message)msg.getContent() );
    super.notify( msg );
  }

  /**
   * Join a group
   *
   * @param newM    Member to add in the group
   * @param groupID Group name to join
   * @throws java.rmi.RemoteException
   */
  @Override
  public synchronized void join ( RemoteMember newM, String groupID ) throws RemoteException
  {
//    System.out.println("HEY");
    //owner.join(newM, groupID);
    super.notifyJoin( newM, groupID );
  }

  /**
   * Register an new observer to be notify when CommMember get stuffs from
   * others
   * @param ob observer to register
   */
  public void addObserver(ComObserver ob) {
    System.out.println("OB");
    System.out.println(this);	
    super.addObserver( ob );
  }

  /**
   * Which RemoteObjects are unreachable ?
   * The list is clean after each call
   * @return Collection containing unreachable RemoteMembers
   */
  public Collection<RemoteMember> getUnreachableRemoteObjects()
  {
    return multicastStrategy.getUnreachableMembers();
  }

  @Override
  public String getId() {
	  return this.id;
  }

  public void setId(String id) {
	  this.id = id;
  }

  @Override
  public void addToView(RemoteMember m, String id) {
	  super.notifyAddToView(m, id);
  }

  @Override
  public void updateLeader(RemoteMember newLead, String groupId) {
	  super.notifyNewLeader( newLead, groupId );
  }
}

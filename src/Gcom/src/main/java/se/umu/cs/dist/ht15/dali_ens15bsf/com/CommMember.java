package se.umu.cs.dist.ht15.dali_ens15bsf.com;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

/**
 * Created by ens15bsf on 2015-10-08.
 * Define the remote object used to manipulate members in Gcom
 */
public class CommMember extends ComObservable implements RemoteMember, Serializable
{
  private static final long serialVersionUID = 4672654439762386594L;
  protected ArrayList<RemoteMember> group;
  //protected Member owner;
  protected MulticastStrategy multicastStrategy;
  //private Vector<ComObserver> observers;


  /**
   * Create a new node
   *
   * @param strategy Strategy to use for multicasting messages
   */
  public CommMember ( MulticastStrategy strategy )
  {
    group = new ArrayList<RemoteMember>();
    multicastStrategy = strategy;
    multicastStrategy.setOwner( this );
    //observers = new Vector<ComObserver>();
  }

  /**
   * Send a message by multicast to the other nodes
   *
   * @param msg   Message to multicast
   * @param group Group to send the message
   */
  public void post ( CommMessage msg, Collection<RemoteMember> group ) throws UnreachableRemoteObjectException
  {
    multicastStrategy.send( msg, group );
  }

  /**
   * Receive a Communication message from another node
   *
   * @param msg Message to receive
   * @throws java.rmi.RemoteException
   */
  @Override
  public void deliver ( CommMessage msg ) throws RemoteException
  {
    //multicastStrategy.receive( msg );
    notify( msg );
  }

  /**
   * Join a group
   *
   * @param newM    Member to add in the group
   * @param groupID Group name to join
   * @throws java.rmi.RemoteException
   */
  @Override
  public void join ( RemoteMember newM, String groupID ) throws RemoteException
  {
    notifyJoin( newM, groupID );
  }

  /**
   * Register an new observer to be notify Zhen CommMember get stuffs from
   * others
   * @param ob observer to register
   */
  public void addObserver(ComObserver ob) {
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
}

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
public class CommMember implements ComObservable, RemoteMember, Serializable
{
  private static final long serialVersionUID = 4672654439762386594L;
  protected ArrayList<RemoteMember> group;
  //protected Member owner;
  protected MulticastStrategy multicastStrategy;
  private Vector<ComObserver> observers;
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
    observers = new Vector<ComObserver>();
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
    for( ComObserver ob : observers )
      ob.notifyObservers( msg );
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
    //owner.join( newM, groupID );
    for( ComObserver ob : observers )
      ob.notifyNewMember( newM, groupID );

  }


  /**
   * Register a new observer to the observable object
   *
   * @param ob observer to register
   */
  @Override
  public void addObserver ( ComObserver ob )
  {
    observers.add( ob );
  }
}

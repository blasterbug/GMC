package se.umu.cs.dist.ht15.dali_ens15bsf.com;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by ens15bsf on 2015-10-08.
 * Tree based multicast, rather than sending to everyone the message,
 * send only the message to our "neighbours"
 */
public class TreeBaseMulticast extends MulticastStrategy
{
  // remember the last send message which don't get back
  private ArrayList<CommMessage> lastSend;
  /**
   * Create a new node
   *
   * @param owner Communication manager owning the node
   */
  public TreeBaseMulticast ( MemberRemote owner )
  {
    this.owner = owner;
    view = new ArrayList<MemberRemote>();
    lastSend = new ArrayList<CommMessage>();

  }

  /**
   * Strategy to multicast a message
   *
   * @param msg   Message to send
   * @param group Group to send the message
   * @throws java.rmi.RemoteException
   */
  @Override
  public void send ( CommMessage msg, Collection<MemberRemote> group ) throws RemoteException
  {
    // update the view
    view = new ArrayList<MemberRemote>( group );
    // get the place of the owner in the view
    int idx = ( (ArrayList)view ).indexOf( owner );
    // variable to store the child
    MemberRemote child;
    // send message to the "left child"
    try
    {
      child = ( (ArrayList<MemberRemote>) view ).get( idx - 1 );
      child.deliver( msg );
      lastSend.add( msg );
    } catch ( IndexOutOfBoundsException e )
    {
    } // do nothing
    // send message to the left child
    try
    {
      child = ( (ArrayList<MemberRemote>) view ).get( idx + 1 );
      child.deliver( msg );
      lastSend.add( msg );
    } catch ( IndexOutOfBoundsException e )
    {
    }  // do nothing
  }

  /**
   * receive a message regarding the used strategy
   *
   * @param msg Incoming message
   * @throws java.rmi.RemoteException
   */
  @Override
  public void receive ( CommMessage msg ) throws RemoteException
  {
    // if I am the sender
    if ( lastSend.contains( msg ) )
    {
      // remove the message from the queue
      lastSend.remove( msg );
    }
    else
    {
      // deliver it
      owner.deliver( msg );
      // then broadcast it to the sub-tree
      this.send( msg, view );
    }
  }
}

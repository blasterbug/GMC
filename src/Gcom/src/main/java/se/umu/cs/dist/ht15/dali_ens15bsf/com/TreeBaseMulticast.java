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
   */
  public TreeBaseMulticast ()
  {
    //this.owner = owner;
    view = new ArrayList<RemoteMember>();
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
  public void send ( CommMessage msg, Collection<RemoteMember> group ) throws RemoteException
  {
    // if the message has not a source,
    // i.e. This member is the source
    if( null == msg.source )
    {
      // add it to the message
      msg.source = owner;
    }
    // update the view
    view = new ArrayList<RemoteMember>( group );
    // get the place of the owner in the view
    int idx = ( (ArrayList)view ).indexOf( owner );
    // variable to store the child
    RemoteMember child;
    // send message to the "left child"
    try
    {
      child = ( (ArrayList<RemoteMember>) view ).get( idx - 1 );
      child.deliver( msg );
      lastSend.add( msg );
    } catch ( IndexOutOfBoundsException e )
    {
    } // do nothing
    // send message to the left child
    try
    {
      child = ( (ArrayList<RemoteMember>) view ).get( idx + 1 );
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

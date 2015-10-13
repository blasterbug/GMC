package se.umu.cs.dist.ht15.dali_ens15bsf.com;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by ens15bsf on 2015-10-08.
 */
public class BasicReliableMulticast extends MulticastStrategy
{

  /**
   * Create a new node
   *
   * @param owner Communication manager owning the node
   */
  public BasicReliableMulticast ( RemoteMember owner )
  {
    this.owner = owner;
    view = new LinkedList<RemoteMember>();
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
    view = group;
    msg.setSource( owner );
    for( RemoteMember member : view )
    {
      member.deliver( msg );
    }
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
    // if I am not the sender
    if( owner != msg.getSource() )
    {
      // get the message
      owner.deliver( msg );
    }
  }
}

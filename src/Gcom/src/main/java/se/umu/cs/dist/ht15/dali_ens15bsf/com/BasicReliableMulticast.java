package se.umu.cs.dist.ht15.dali_ens15bsf.com;

import se.umu.cs.dist.ht15.dali_ens15bsf.groupmanagement.Member;

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
  public BasicReliableMulticast ( MemberRemote owner )
  {
    this.owner = owner;
    view = new LinkedList<MemberRemote>();
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
    view = group;
    msg.setSource( owner );
    for( MemberRemote member : view )
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

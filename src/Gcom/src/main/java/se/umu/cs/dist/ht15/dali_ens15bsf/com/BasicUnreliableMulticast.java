package se.umu.cs.dist.ht15.dali_ens15bsf.com;

import java.rmi.RemoteException;
import java.util.Collection;

/**
 * Created by ens15bsf on 2015-10-08.
 * Define the strategy to multicast message a message within a group,
 * the <i>source node</i> sends the message to every node in the view.
 */
public class BasicUnreliableMulticast extends MulticastStrategy
{

  /**
   * Create a new node
   */
  public BasicUnreliableMulticast ()
  {
    super();
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
    // update the view, i.e. the group
    view = group;
    // the message is send from here
    msg.setSource( owner );
    // for each memeber of the group
    for ( RemoteMember member : view )
    {
      // send the message
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
    if ( owner != msg.getSource() )
    {
      // get the message
      owner.deliver( msg );
    }
  }
}

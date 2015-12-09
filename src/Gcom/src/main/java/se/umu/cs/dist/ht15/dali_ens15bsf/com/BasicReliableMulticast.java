package se.umu.cs.dist.ht15.dali_ens15bsf.com;

import java.rmi.RemoteException;
import java.util.LinkedList;

/**
 * Created by ens15bsf on 2015-10-13.
 */
public class BasicReliableMulticast extends BasicUnreliableMulticast
{
  private LinkedList<ComMessage> receivedBefore;

  public BasicReliableMulticast ()
  {
    super();
    receivedBefore = new LinkedList<ComMessage>();
  }

  /**
   * receive a message regarding the used strategy
   *
   * @param msg Incoming message
   * @throws java.rmi.RemoteException
   * @throws se.umu.cs.dist.ht15.dali_ens15bsf.com.UnreachableRemoteObjectException
   */
  @Override
  public void receive ( ComMessage msg ) throws RemoteException, UnreachableRemoteObjectException
  {
    super.printPath( msg );
    // if the message was not previously received
    if ( msg.getPath().contains( nodeID ) )
    if ( !( receivedBefore.contains( msg ) ) )
    {
      // if the process is not the sender
      if ( this == msg.getSource() )
      {
        // multicast the message
        send( msg, view );
        // TODO : get and use an updated view
      }

    }
    owner.queue( msg );
  }
}

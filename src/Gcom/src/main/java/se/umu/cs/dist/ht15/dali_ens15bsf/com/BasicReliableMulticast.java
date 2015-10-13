package se.umu.cs.dist.ht15.dali_ens15bsf.com;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by ens15bsf on 2015-10-13.
 */
public class BasicReliableMulticast extends BasicUnreliableMulticast
{
  private LinkedList<CommMessage> receivedBefore;

  public BasicReliableMulticast()
  {
    super();
    receivedBefore = new LinkedList<CommMessage>();
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
    // if the message was not previously received
    if( !( receivedBefore.contains( msg ) ))
    {
      // if the process is not the sender
      if( this == msg.getSource() )
      {
        // multicast the message
        send( msg, view );
        // TODO : get amd use an updated view
      }

    }
    owner.deliver( msg );
  }
}

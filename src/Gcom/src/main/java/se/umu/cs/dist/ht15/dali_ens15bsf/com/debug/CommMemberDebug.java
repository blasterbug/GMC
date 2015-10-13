package se.umu.cs.dist.ht15.dali_ens15bsf.com.debug;

import se.umu.cs.dist.ht15.dali_ens15bsf.com.*;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by ens15bsf on 2015-10-13.
 * Define a way to debug and simulate network failure
 */
public class CommMemberDebug extends CommMember
{
  private LinkedList<CommMessage> delayedMessages;
  private long messageDelay;

  /**
   * Create a new node
   *
   * @param strategy Strategy to use for multicasting messages
   */
  public CommMemberDebug ( MulticastStrategy strategy )
  {
    super( new StrategyDebug( strategy ) );
    delayedMessages = new LinkedList<CommMessage>();
    messageDelay = 0;
  }

  /**
   * Set the amont of time to wait before sending a message
   * @param delay delay to wait before sending message, in millis
   */
  public void setMessageDelay( long delay )
  {
    messageDelay = delay;
  }

  public void setRandomDelivering( boolean active )
  {
    ((StrategyDebug)multicastStrategy).setChangeDeliveringOrder( active );
  }

  /**
   * Send a message to the group
   * @param msg   Message to multicast
   * @param group Group to send the message
   * @throws UnreachableRemoteObjectException
   */
  @Override
  public void post ( CommMessage msg, Collection<RemoteMember> group ) throws UnreachableRemoteObjectException
  {
    try
    {
      Thread.sleep( messageDelay );
    } catch ( InterruptedException e )
    {
      e.printStackTrace();
    }
    multicastStrategy.send( msg, group );
  }
}

package se.umu.cs.dist.ht15.dali_ens15bsf.com.debug;

import se.umu.cs.dist.ht15.dali_ens15bsf.com.*;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Vector;

/**
 * Created by ens15bsf on 2015-10-13.
 * Define a way to debug and simulate network failure
 */
public class ComMemberDebug extends ComMember
{
  private LinkedList<ComMessage> delayedMessages;
  private long messageDelay;
  private Vector<ComDebugObserver> observers;

  /**
   * Create a new node
   *
   * @param strategy Strategy to use for multicasting messages
   */
  public ComMemberDebug ( MulticastStrategy strategy, ComObserver mbr )
  {
    super( new StrategyDebug( strategy ), mbr );
    delayedMessages = new LinkedList<ComMessage>();
    messageDelay = 0;
  }

  /**
   * Set the amont of time to wait before sending a message
   *
   * @param delay delay to wait before sending message, in millis
   */
  public void setMessageDelay ( long delay )
  {
    messageDelay = delay;
  }

  /**
   * Set if all the messages should be delivered or maybe not
   * @param active True to activated the random delivery
   */
  public void setRandomDelivering ( boolean active )
  {
    ( (StrategyDebug) multicastStrategy ).setChangeDeliveringOrder( active );
  }

  /**
   * Send a message to the group
   *
   * @param msg   Message to multicast
   * @param group Group to send the message
   * @throws UnreachableRemoteObjectException
   */
  @Override
  public void post ( ComMessage msg, Collection<RemoteMember> group ) throws UnreachableRemoteObjectException
  {
    try
    {
      Thread.sleep( messageDelay );
    } catch ( InterruptedException e )
    {
      e.printStackTrace();
    }
    multicastStrategy.send( msg, group );
    //for ( ComDebugObserver obs  )
  }
}

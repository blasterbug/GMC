package se.umu.cs.dist.ht15.dali_ens15bsf.com.debug;

import se.umu.cs.dist.ht15.dali_ens15bsf.com.ComMessage;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.MulticastStrategy;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.RemoteMember;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.UnreachableRemoteObjectException;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

/**
 * Define a pair in Java.
 * A pair is a data structure with two elemenets,
 * a left and a right element.
 */
class Pair<L, R>
{
  private L left;
  private R right;

  /**
   * Create a pair
   *
   * @param arg1 Left element of the pair
   * @param arg2 Right element of the pair
   */
  public Pair ( L arg1, R arg2 )
  {
    left = arg1;
    right = arg2;
  }

  /**
   * Get the left element of the pair
   *
   * @return Left element of the pair
   */
  public L getLeft ()
  {
    return left;
  }

  /**
   * Get the right element of the pair
   *
   * @return Right element of the pair
   */
  public R getRight ()
  {
    return right;
  }

  @Override
  public String toString ()
  {
    return "(" + left + "; " + right + ")";
  }
}

/**
 * Define a way to debug how packets will be send and received
 */
public class StrategyDebug extends MulticastStrategy
{
  private MulticastStrategy core;
  private LinkedList<Pair<ComMessage, Collection>> sendQueue;
  private LinkedList<ComMessage> incomingQueue;
  private boolean mixDelivering;
  private boolean mixReceiving;
  private Random dice;

  public StrategyDebug ( MulticastStrategy strg )
  {
    core = strg;
    dice = new Random();
    sendQueue = new LinkedList<Pair<ComMessage, Collection>>();
    incomingQueue = new LinkedList<ComMessage>();
    mixDelivering = false;
    mixReceiving = false;
  }

  /**
   * Strategy to multicast a message
   *
   * @param msg   Message to send
   * @param group Group to send the message
   * @throws UnreachableRemoteObjectException
   */
  @Override
  public void send ( ComMessage msg, Collection<RemoteMember> group ) throws UnreachableRemoteObjectException
  {
    if ( mixDelivering )
    {
      sendQueue.add( new Pair<ComMessage, Collection>( msg, group ) );
      Collections.shuffle( sendQueue );
    }
    // randomly deliver message
    if ( mixDelivering && dice.nextBoolean() )
    {
      Pair<ComMessage, Collection> toSend = sendQueue.pop();
      core.send( toSend.getLeft(), toSend.getRight() );
    }
  }

  /**
   * receive a message regarding the used strategy
   *
   * @param msg Incoming message
   * @throws java.rmi.RemoteException
   */
  @Override
  public void receive ( ComMessage msg ) throws RemoteException
  {
    try
    {
      incomingQueue.add( msg );
      // if random receiving is atived, then deliver messages if the dice want to
      if ( mixReceiving && dice.nextBoolean() )
      {
        core.receive( incomingQueue.pop() );
      }
      else
      {
        // else, just deliver the message
        core.receive( incomingQueue.pop() );
      }
    }
    catch ( UnreachableRemoteObjectException exp )
    {}
  }


  /**
   * Set the message delivering order
   *
   * @param active If true, randomly mix incoming messages
   */
  public void setChangeDeliveringOrder ( boolean active )
  {
    mixDelivering = active;
  }

  /**
   * Set to randomize message receiving
   *
   * @param active If true, randomly mix incoming messages
   */
  public void setMixReceiving ( boolean active )
  {
    mixReceiving = active;
  }
}

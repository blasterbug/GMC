package se.umu.cs.dist.ht15.dali_ens15bsf.com.debug;

import se.umu.cs.dist.ht15.dali_ens15bsf.com.ComMember;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.ComMessage;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.ComObserver;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.MulticastStrategy;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.Vector;

/**
 * Created by ens15bsf on 2015-10-13.
 * Define a way to debug and simulate network failure
 */
public class ComMemberDebug extends ComMember
{
  private LinkedList<ComMessage> delayedMessages;
  private Vector<ComMemberDebugObserver> observers;


  public ComMemberDebug ( MulticastStrategy strategy, ComObserver mbr )
  {
    super( strategy, mbr );
    delayedMessages = new LinkedList<>();
    observers = new Vector<>();
  }


  public void addObserver( ComMemberDebugObserver observer )
  {
    observers.add( observer );
  }

  public void deliverWithDelay ( int index ) throws RemoteException
  {
    super.deliver( delayedMessages.get( index ) );
  }

  /**
   * Receive a Communication message from another node
   * and put it in a queue
   * @param msg Message to receive
   */
  @Override
  public void deliver ( ComMessage msg )
  {
    delayedMessages.add( msg );
    for( ComMemberDebugObserver obs : observers )
      obs.notifyQueued( delayedMessages.indexOf( msg ), msg );
  }
}

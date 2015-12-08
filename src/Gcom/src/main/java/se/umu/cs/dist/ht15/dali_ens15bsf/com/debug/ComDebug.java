package se.umu.cs.dist.ht15.dali_ens15bsf.com.debug;

import se.umu.cs.dist.ht15.dali_ens15bsf.com.ComMember;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.ComMessage;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.ComObserver;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.RemoteMember;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Vector;

/**
 * Created by ens15bsf on 07/12/15.
 */
public final class ComDebug implements ComObserver, Serializable
{
  private static final long serialVersionUID = 2550506719036192896L;
  private final Vector<DelayedComMessage> delayedQueue;
  private final Vector<ComDebugObserver> debugObservers;
  private final Vector<ComObserver> coreObservers;
  private final ComMember coreDebug;
  private Vector<ComDebugObserver> observers;



  /**
   * Create a new node
   *
   * @param remoteMember remote Member to debug (com layer)
   * @param groupManager Member to debug (group management layer)
   */
  public ComDebug ( ComMember remoteMember, ComObserver groupManager )
  {
    //super();
    delayedQueue = new Vector<>();
    debugObservers = new Vector<>();
    coreObservers = new Vector<>();
    coreDebug = remoteMember;
    coreObservers.add( groupManager );
    remoteMember.addObserver( this );
  }

  public void addDebugObserver( ComDebugObserver obs )
  {
    debugObservers.add( obs );
  }

  public void addCoreObserver( ComObserver obs )
  {
    coreObservers.add( obs );
  }

  public void removeDebugObserver( ComDebugObserver obs )
  {
    debugObservers.remove( obs );
  }

  public void removeCoreObserver( ComObserver obs )
  {
    coreObservers.remove( obs );
  }

  public void deliver( int messageIndex ) throws RemoteException
  {
    DelayedComMessage msg = delayedQueue.remove( messageIndex );
    for ( ComObserver obs : coreObservers )
      obs.notifyObservers( msg.getContent() );
    for ( ComDebugObserver obs : debugObservers )
      obs.notifyunQueued( messageIndex );
  }

  /**
   * Notify Observers when a new incoming message arrive
   *
   * @param msg message to give to the Observer
   */
  @Override
  public void notifyObservers ( ComMessage msg )
  {
    delayedQueue.add( new DelayedComMessage( msg ) );
    for ( ComDebugObserver obs : debugObservers )
      obs.notifyQueued( delayedQueue.indexOf( msg ), msg.toString() );
  }

  /**
   * Notify observer when new member want to join a group
   *
   * @param member  New member joining to the group
   * @param groupID Group name to join
   */
  @Override
  public void notifyNewMember ( RemoteMember member, String groupID )
  {
    for ( ComObserver obs : coreObservers )
      obs.notifyNewMember( member, groupID );
  }

  @Override
  public void notifyAddToView ( RemoteMember m, String id )
  {
    for ( ComObserver obs : coreObservers )
      obs.notifyAddToView( m, id );
  }

  @Override
  public void notifyNewLeader ( RemoteMember newLeader, String groupId )
  {
    for ( ComObserver obs : coreObservers )
      obs.notifyNewLeader( newLeader, groupId );
  }

  @Override
  public void notifyRemoveFromView ( RemoteMember m, String id )
  {
    for ( ComObserver obs : coreObservers )
      obs.notifyRemoveFromView( m, id );
  }
}

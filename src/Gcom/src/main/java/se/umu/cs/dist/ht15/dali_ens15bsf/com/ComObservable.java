package se.umu.cs.dist.ht15.dali_ens15bsf.com;

import java.util.Vector;

/**
 * Created by ens15bsf on 2015-10-13.
 * Observer pattern for the communication layer in Gcom
 */
public class ComObservable
{
  private Vector<ComObserver> observers = new Vector<ComObserver>();


  /**
   * Register a new observer to the observable object
   *
   * @param ob observer to register
   */
  public void addObserver ( ComObserver ob )
  {
    observers.add( ob );
  }

  /**
   * Notify observer when a new member wants to joint a group
   *
   * @param newM    joining member
   * @param groupID Group name to join
   */
  void notifyJoin ( RemoteMember newM, String groupID )
  {
    //owner.join( newM, groupID );
    for ( ComObserver ob : observers )
      ob.notifyNewMember( newM, groupID );
  }

}

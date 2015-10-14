package se.umu.cs.dist.ht15.dali_ens15bsf.com;

import java.util.Vector;

/**
 * Created by ens15bsf on 2015-10-13.
 * Observer pattern for the communication layer in Gcom
 */
public class ComObservable
{
  private Vector<ComObserver> observers;

  public ComObservable() {
	  observers = new Vector<ComObserver>();
  }


  /**
   * Register a new observer to the observable object
   *
   * @param ob observer to register
   */
  public void addObserver ( ComObserver ob )
  {
System.out.println("OB2");
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
    System.out.println("Just do it!");
    System.out.println("THIS: " + this);	
    System.out.println("Nr of OBS: "+observers.size());	
    for ( ComObserver ob : observers ) {
	System.out.println(ob);	
      	ob.notifyNewMember( newM, groupID );
    }
  }

  /**
   * Notify observers on incoming messages
   * @param msg New incoming message
   */
  void notify ( CommMessage msg )
  {
    for ( ComObserver ob : observers )
      ob.notifyObservers( msg );
  }


}

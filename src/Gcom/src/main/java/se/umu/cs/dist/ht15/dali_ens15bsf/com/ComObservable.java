package se.umu.cs.dist.ht15.dali_ens15bsf.com;

import java.util.Vector;

/**
 * Created by ens15bsf on 2015-10-13.
 * Observer pattern for the communication layer in Gcom
 */
class ComObservable<T extends ComObserver>
{
  private Vector<T> observers;

  public ComObservable() {
	  observers = new Vector<T>();
  }


  /**
   * Register a new observer to the observable object
   *
   * @param obs observer to register
   */
  public void addObserver ( T obs )
  {
    observers.add( obs );
  }

  /**
   * Remove an observer to the observable object
   *
   * @param obs observer to remove
   */
  public void removeObserver ( T obs )
  {
    observers.remove( obs );
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
    //System.out.println("Just do it!");
    //System.out.println("THIS: " + this);	
    //System.out.println("Nr of OBS: "+observers.size());	
    for ( ComObserver ob : observers ) {
	//System.out.println("OBs "+ob);	
      	ob.notifyNewMember( newM, groupID );
    }
  }

  public void notifyAddToView(RemoteMember m, String id) {
	  for ( ComObserver ob : observers ) {
		  ob.notifyAddToView(m, id);
	  }
  }

  public void notifyNewLeader(RemoteMember newLead, String groupId) {
    for ( ComObserver ob : observers ) {
		  ob.notifyNewLeader( newLead, groupId );
	  }
  }

  public void notifyRemoveFromView(RemoteMember m, String id) {
	  for ( ComObserver ob : observers )
		  ob.notifyRemoveFromView(m, id);
  }

  /**
   * Notify observers on incoming messages
   * @param msg New incoming message
   */
  void notify ( ComMessage msg )
  {
    for ( ComObserver ob : observers )
      ob.notifyObservers( msg );
  }
}

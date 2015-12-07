package se.umu.cs.dist.ht15.dali_ens15bsf;

import se.umu.cs.dist.ht15.dali_ens15bsf.com.MulticastStrategy;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.debug.ComMemberDebugObserver;
import se.umu.cs.dist.ht15.dali_ens15bsf.debug.GcomDebugObserver;
import se.umu.cs.dist.ht15.dali_ens15bsf.nameserver.NamingServiceUnavailableException;
import se.umu.cs.dist.ht15.dali_ens15bsf.ordering.Orderer;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Vector;

/**
 * Created by ens15bsf on 2015-11-18.
 */
public class GcomDebug<T> extends GcomProxy implements GcomObserver
{

  private Vector<GcomDebugObserver> observers = new Vector<>();;
  //private Orderer orderDebug;
  //private MulticastStrategy multicasterDebug;


  public GcomDebug( Orderer order, MulticastStrategy ms ) throws RemoteException, NamingServiceUnavailableException
  {
    super( order, ms );
    //mbr = new MemberImplDebug( order, ms );
  }

  /**
   * Register a new observer
   * @param obs observer
   */
  public void addObserver( GcomDebugObserver obs )
  {
    observers.add( obs );
  }

  /**
   * Delete a new observer
   * @param obs observer
   */
  public void removeObserver( GcomDebugObserver obs )
  {
    observers.remove( obs );
  }

  /**
   * Register a new observer to the com layer
   * @param obs observer
   */
  public void addObserverComDebug( ComMemberDebugObserver obs )
  {
    //((MemberImplDebug)mbr).addObserverComMemberDebug( obs );
  }

  /**
   * Delete a new observer to the com layer
   * @param obs observer
   */
  public void removeObserverComDebug( ComMemberDebugObserver obs )
  {
    //((MemberImplDebug)mbr).removeObserverComMemberDebug( obs );
  }

  /**
   * Send a message to the group
   *
   * @param msg message to send
   */
  @Override
  public void send ( Serializable msg )
  {
    for ( GcomDebugObserver obs : observers )
      obs.notifyOutgoingMessage( msg );
    super.send( msg );
  }

  @Override
  public void newMessage ( Message msg )
  {
    for ( GcomDebugObserver obs : observers )
      obs.notifyIncomingMessage( msg );
  }

  /**
   * Join a group of member
   *
   * @param groupid name of the group to join
   */
  @Override
  public void join ( String groupid ) throws CantJoinException
  {
    super.join( groupid );
    for ( GcomDebugObserver obs : observers )
      obs.notifyJoin( groupid );
  }

  /**
   * Connect the member to the NameServer, should be called first.
   *
   * @throws se.umu.cs.dist.ht15.dali_ens15bsf.nameserver.NamingServiceUnavailableException
   */
  @Override
  public void connect () throws NamingServiceUnavailableException
  {
    for ( GcomDebugObserver obs : observers )
      obs.notifyConnect();
    super.connect();
  }
}

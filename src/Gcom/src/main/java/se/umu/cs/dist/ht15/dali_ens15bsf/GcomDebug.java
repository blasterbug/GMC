package se.umu.cs.dist.ht15.dali_ens15bsf;

import se.umu.cs.dist.ht15.dali_ens15bsf.debug.GcomDebugObserver;
import se.umu.cs.dist.ht15.dali_ens15bsf.nameserver.NamingServiceUnavailableException;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Vector;

/**
 * Created by ens15bsf on 2015-11-18.
 */
public class GcomDebug<T> extends GcomProxy implements GcomObserver
{

  private Vector<GcomDebugObserver> observers;

  public GcomDebug ( Gcom module ) throws RemoteException, NamingServiceUnavailableException
  {
    super( ((GcomProxy)module).getOrderingStrategy() , ((GcomProxy)module).getMulticastStrategy() );
    module.addObserver( this );
    observers = new Vector<>();
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

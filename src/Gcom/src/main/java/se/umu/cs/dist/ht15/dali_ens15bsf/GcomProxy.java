package se.umu.cs.dist.ht15.dali_ens15bsf;

import se.umu.cs.dist.ht15.dali_ens15bsf.com.MulticastStrategy;
import se.umu.cs.dist.ht15.dali_ens15bsf.groupmanagement.MemberImpl;
import se.umu.cs.dist.ht15.dali_ens15bsf.nameserver.NamingService;
import se.umu.cs.dist.ht15.dali_ens15bsf.nameserver.NamingServiceRemote;
import se.umu.cs.dist.ht15.dali_ens15bsf.nameserver.NamingServiceUnavailableException;
import se.umu.cs.dist.ht15.dali_ens15bsf.ordering.Orderer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

/**
 * Created by ens15bsf on 2015-11-05.
 * Gcom is a middleware designed in a peer-to-peer approach.
 * All nodes, the member are huddled in groups.
 */
public class GcomProxy implements Observer, Gcom
{
  private MemberImpl mbr;
  private Vector<GcomObserver> observers;


  public GcomProxy( Orderer order, MulticastStrategy ms ) throws RemoteException
  {
    observers = new Vector<GcomObserver>();
    mbr = new MemberImpl( order, ms );
    mbr.addObserver( this );
  }

  /**
   * Connect the member to the NameServer, should be called first.
   */
  public void connect()
  {
    try
    {
      mbr.connectToNameserver();
    } catch ( RemoteException e )
    {
      e.printStackTrace();
    } catch ( NamingServiceUnavailableException e )
    {
      e.printStackTrace();
    }
  }

  /**
   * Join a group of member
   * @param groupid name of the group to join
   */
  public void join( String groupid )
  {
    try
    {
      mbr.joinGroup( groupid );
    } catch ( RemoteException e )
    {
      e.printStackTrace();
    }
  }

  /**
   * Send a message to the group
   * @param msg message to send
   */
  public void send( Message msg )
  {
    mbr.sendMessage( msg );
  }

  /**
   * Add an observer to be notified when new message arrived
   *
   * @param obs Observer to notify
   */
  @Override
  public void addObserver ( GcomObserver obs )
  {
    observers.add( obs );
  }

  @Override
  public void update ( Observable observable, Object o )
  {
    Message msg = (Message)o;
    for ( GcomObserver obs : observers )
      obs.newMessage( msg );
  }

  public void updateAvailableGroups()
  {

  }
}

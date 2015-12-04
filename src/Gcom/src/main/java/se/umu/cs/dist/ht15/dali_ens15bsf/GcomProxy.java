package se.umu.cs.dist.ht15.dali_ens15bsf;

import se.umu.cs.dist.ht15.dali_ens15bsf.com.MulticastStrategy;
import se.umu.cs.dist.ht15.dali_ens15bsf.groupmanagement.MemberImpl;
import se.umu.cs.dist.ht15.dali_ens15bsf.nameserver.NamingServerFactory;
import se.umu.cs.dist.ht15.dali_ens15bsf.nameserver.NamingServiceRemote;
import se.umu.cs.dist.ht15.dali_ens15bsf.nameserver.NamingServiceUnavailableException;
import se.umu.cs.dist.ht15.dali_ens15bsf.ordering.Orderer;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

/**
 * Created by ens15bsf on 2015-11-05.
 * Gcom is a middleware designed in a peer-to-peer approach.
 * All nodes, the member are huddled in groups.
 */
class GcomProxy<T extends Serializable> implements Observer, Gcom
{
  protected NamingServiceRemote nsRemote;
  protected MemberImpl mbr;
  protected Vector<GcomObserver> observers;
  protected final Orderer order;
  protected final MulticastStrategy ms;


  /**
   * Create a Gcom module object
   * @param order Ordering strategy used to order the messages
   * @param ms Multicast strategy used to send messages to peers
   * @throws RemoteException
   * @throws NamingServiceUnavailableException
   */
  public GcomProxy( Orderer order, MulticastStrategy ms ) throws RemoteException, NamingServiceUnavailableException
  {
    this.order = order;
    this.ms = ms;
    nsRemote = NamingServerFactory.NamingService();
    observers = new Vector<GcomObserver>();
    mbr = new MemberImpl( order, ms );
    mbr.addObserver( this );
  }

  protected Orderer getOrderingStrategy()
  {
    return order;
  }

  protected MulticastStrategy getMulticastStrategy()
  {
    return ms;
  }

  /**
   * Connect the member to the NameServer, should be called first.
   * @exception NamingServiceUnavailableException
   */
  public void connect() throws NamingServiceUnavailableException
  {
    try
    {
      mbr.connectToNameserver();
      mbr.updateIdFromNameServer();
    }
    catch ( RemoteException | NamingServiceUnavailableException e )
    {
      throw new NamingServiceUnavailableException( e.getMessage() );
    }
  }

  /**
   * Get the list of all available group
   *
   * @return List of the available groups name, return null if Naming Server is unavailable
   */
  @Override
  public String[] getGroups ()
  {
    try
    {
      LinkedList<String> groupsLL = nsRemote.getGroups();
      String[] res = new String[groupsLL.size()];
      for ( int i = 0 ; i < res.length ; i++ )
        res[i] = groupsLL.get( i );
      return groupsLL.toArray( res );
    }
    catch ( RemoteException e )
    {
      System.err.println( e.getMessage() );
      return new String[0];
    }
  }

  /**
   * Join a group of member
   * @param groupid name of the group to join
   */
  public void join( String groupid ) throws CantJoinException
  {
    System.out.println("hello");
    try
    {
      mbr.joinGroup( groupid );
    } catch ( RemoteException e )
    {
      System.err.println( e.getMessage() );
      throw new CantJoinException( e.getMessage() );
    }
    for ( GcomObserver obs : observers )
      obs.join( groupid );
  }


  /**
   * Send a message to the group
   * @param msg message to send
   */
  public void send( Serializable msg )
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

}

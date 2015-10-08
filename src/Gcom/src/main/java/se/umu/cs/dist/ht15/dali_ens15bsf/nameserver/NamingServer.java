package se.umu.cs.dist.ht15.dali_ens15bsf.nameserver;

import se.umu.cs.dist.ht15.dali_ens15bsf.com.MemberRemote;

import java.io.Serializable;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


/**
 * Created by ens15bsf on 2015-10-06.
 * RMI server, start when no other master node exists or crashed
 * The server keep a list and reference to all the remote objects (nodes)
 * within the network
 *
 * @serial
 */
public class NamingServer implements Serializable, NamingServiceRemote
{
  //// Name of the GCOM Node server
  public static final String SERVER_NAME = "GCOM_NAMING_SERVICE";
  /// port for the master node
  public static final int SERVER_PORT = 1200;
  private static final long serialVersionUID = -2088012373960946539L;
  /// List of the nodes
  protected Map<String, MemberRemote> groups;
  /// Server to create
  protected UnicastRemoteObject server;
  /// Registry of remote objects
  protected Registry directory;

  /**
   * Create a new master node
   *
   * @throws RemoteException
   */
  public NamingServer () throws RemoteException, AlreadyBoundException
  {
    groups = new HashMap<String, MemberRemote>();
    // make the server reachable
    NamingServiceRemote mn = (NamingServiceRemote) UnicastRemoteObject.exportObject( this, 0 );
    //server.exportObject( this );
    directory = LocateRegistry.createRegistry( SERVER_PORT );
    directory.bind( SERVER_NAME, this );
    System.out.println( "Naming server is running..." );
  }

  @Override
  public MemberRemote joinGroup ( String groupName, MemberRemote m ) throws RemoteException
  {

    MemberRemote leader = groups.get( groupName );
    if ( null != leader )
    {
      leader.join( m );
      System.out.println( "Server : Member " + m.getMemberID() + " registered" );
    }
    return leader;
  }


  @Override
  public LinkedList<String> getGroups () throws RemoteException
  {
    return new LinkedList<String>( groups.keySet() );
  }

  /**
   * Get the leader of a given group
   *
   * @param groupName Name of group
   * @return leader for the given group
   */
  @Override
  public MemberRemote getLeader ( String groupName ) throws RemoteException
  {
    return groups.get( groupName );
  }

}
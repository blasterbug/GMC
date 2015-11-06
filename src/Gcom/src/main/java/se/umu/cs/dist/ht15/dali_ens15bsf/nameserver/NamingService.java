package se.umu.cs.dist.ht15.dali_ens15bsf.nameserver;

import se.umu.cs.dist.ht15.dali_ens15bsf.com.RemoteMember;

import java.io.Serializable;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;


/**
 * Created by ens15bsf on 2015-10-06.
 * RMI server, start when no other master node exists or crashed
 * The server keep a list and reference to all the remote objects (nodes)
 * within the network
 *
 * @serial
 */
public class NamingService implements Serializable, NamingServiceRemote
{
  //// Name of the naming service
  public static final String SERVICE_NAME = "GCOM_NAMING_SERVICE";
  //// Name of the GCOM Node server
  public static final String SERVER_NAME = "GCOM_NAMING_SERVER";
  /// port for the master node
  public static final int SERVER_PORT = 1200;
  private static final long serialVersionUID = -2088012373960946539L;
  /// List of the nodes
  protected Map<String, RemoteMember> groups;
  /// Server to create
  protected UnicastRemoteObject server;
  /// Registry of remote objects
  protected Registry directory;
  protected Random dice;
  //protected HashMap<String, RemoteMember> idMap;
  private int memberCounter = 0;


  /**
   * Create a new master node
   *
   * @throws RemoteException
   */
  public NamingService () throws RemoteException, AlreadyBoundException
  {
    dice = new Random();
    groups = new HashMap<String, RemoteMember>();
    //idMap = new HashMap<String, RemoteMember>();
    // make the server reachable
    NamingServiceRemote mn = (NamingServiceRemote) UnicastRemoteObject.exportObject( this, 0 );
    //server.exportObject( this );
    directory = LocateRegistry.createRegistry( SERVER_PORT );
    directory.bind( SERVICE_NAME, this );
    System.out.println( "Naming service is running..." );
  }

  @Override
  public RemoteMember joinGroup ( String groupName, RemoteMember m ) throws RemoteException
  {
    // if the group is already registered
    RemoteMember leader = groups.get( groupName );
    if ( null != leader )
    {
      // just ask to the leader for joining the group
      //m.join(leader, leader.getId());
      leader.join( m, m.getId());
      System.out.println("Member ["+m+"] joining leader ["+leader+"] of group ["+groupName+"]");
    }
    else
    {
      // register the group, with the leader
      groups.put( groupName, m );
      System.out.println( "Server : new leader " + m.toString() + "for group " + groupName );
      // and ask to the leader to join
      m.join( m, m.getId() );
      // m is the leader
      leader = m;
    }
    //directory.rebind( m.toString(), m );
    System.out.println( "Server : Member " + m.toString() + " registered in group " + groupName );
    return leader;
  }


  @Override
  public LinkedList<String> getGroups () throws RemoteException
  {
    LinkedList<String> groupsLL = new LinkedList<String>();
    // check if all the leaders are alive
    for ( String gid : groups.keySet() )
    {
      try {
        groups.get( gid ).getId();
        groupsLL.add( new String( gid ) );
      }
      // if not
      catch ( RemoteException e )
      {
        // remote them from the registery
        System.out.println( "Server : Group " + gid + " removed" );
        groups.remove( gid );
      }
    }
    System.out.println(groupsLL);
    return groupsLL;
  }

  /**
   * Get the leader of a given group
   *
   * @param groupName Name of group
   * @return leader for the given group, null if it doesn't exist
   */
  @Override
  public RemoteMember getLeader ( String groupName ) throws RemoteException
  {
    return groups.get( groupName );
  }

  /**
   * Notify the naming service when a group elected a new leader
   *
   * @param groupName Group name concerned by the changes
   * @param newLeader New leader of the group
   * @throws java.rmi.RemoteException
   */
  @Override
  public void updateLeader ( String groupName, RemoteMember newLeader ) throws RemoteException
  {
    groups.put( groupName, newLeader );
    System.out.println( "Server : Member " + newLeader.toString() + " is the new leader of the group " + groupName );
  }

  /**
   * Ask the server for an ID, Should be done before Every other stuff
   *
   * @param member Member from that to generate the ID
   * @return ID for the given Member in a String
   * @throws java.rmi.RemoteException
   */
  @Override
  public String getMyId ( RemoteMember member ) throws RemoteException
  {
    String id = Integer.toString(memberCounter);
    memberCounter++;
    return id;
    //String id = member.toString();
    //id += Integer.toHexString( dice.nextInt() );
    //idMap.add( id, member )
    //return id;
  }

  @Override
  public void destroy() throws RemoteException { 
	try {
		directory.unbind(SERVICE_NAME);
	} catch (NotBoundException e) {
		//Not a problem
	}
	
  }
}

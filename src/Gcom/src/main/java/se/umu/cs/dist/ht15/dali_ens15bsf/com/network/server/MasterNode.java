package se.umu.cs.dist.ht15.dali_ens15bsf.com.network.server;

import java.io.Serializable;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

import se.umu.cs.dist.ht15.dali_ens15bsf.com.network.NodeAlreadyBoundException;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.network.NodeRemote;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.network.UnkownNodeException;


/**
 * Created by ens15bsf on 2015-10-06.
 * RMI server, start when no other master node exists or crashed
 * The server keep a list and reference to all the remote objects (nodes)
 * within the network
 * @serial
 */
public class MasterNode implements Serializable, MasterNodeRemote
{
  private static final long serialVersionUID = -2088012373960946539L;
  //// Name of the GCOM Node server
  public static final String SERVER_NAME = "GCOM_MASTER_NODE";
  /// port for the master node
  public static final int SERVER_PORT = 1200;
  /// List of the nodes
  protected Map<String, NodeRemote> nodes;
  /// Server to create
  protected UnicastRemoteObject server;
  /// Registry of remote objects
  protected Registry directory;

  /**
   * Create a new master node
   * @throws RemoteException
   * @throws NodeAlreadyBoundException
   */
  public MasterNode () throws NodeAlreadyBoundException
  {
    nodes = new HashMap<String, NodeRemote>();
    // make the server reachable
    try
    {
      MasterNodeRemote mn = (MasterNodeRemote) UnicastRemoteObject.exportObject( this, 0 );
      //server.exportObject( this );
      directory = LocateRegistry.createRegistry( SERVER_PORT );
      directory.bind( SERVER_NAME, this );
      System.out.println("Server is running...");
    }
    // if the server is already bind
    catch ( AlreadyBoundException e )
    {
      // throw an exception
      throw new NodeAlreadyBoundException( "A server is already running!" );
    }
    // for remote exceptions
    catch ( RemoteException e )
    {
      // throw an exception
      // Maybe not the suitable exception
      e.printStackTrace();
      throw new NodeAlreadyBoundException( e.getMessage(), e.getCause() );
    }
  }

  @Override
  public void register( NodeRemote node ) throws NodeAlreadyBoundException, RemoteException
  {
    if( nodes.containsKey( node.getName() ) )
    {
      throw new NodeAlreadyBoundException( "A node is already register under this name" );
    }
    else {
      nodes.put( node.getName(), node );
      System.out.println("Server : Node " + node.getName() + " registered");
    }
  }

  @Override
  public void unregister( String nodeName ) throws RemoteException
  {
    NodeRemote n = nodes.remove( nodeName );
    if( null == n )
    {
      System.err.println( "No node registered for " + nodeName );
    }
  }

  @Override
  public LinkedList<String> getRegisteredNodes()  throws RemoteException
  {
    return new LinkedList<String>( nodes.keySet() );
  }

  @Override
  public NodeRemote getRemoteNode( String name ) throws UnkownNodeException, RemoteException
  {
    if( nodes.containsKey( name ) )
    {
      return nodes.get( name );
    }
    else {
      throw new UnkownNodeException( "No node register under " + name );
    }
  }
}

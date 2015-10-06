package se.umu.cs.dist.ht15.dali_ens15bsf.com.network;

import java.io.Serializable;
import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ens15bsf on 2015-10-06.
 * RMI server, start when no other master node exists or crashed
 * The server keep a list and reference to all the remote objects (nodes)
 * within the network
 */
public class NodeMaster implements NodeMasterInterface
{
  //// Name of the GCOM Node server
  public static final String SERVER_NAME = "GCOM_MASTER_NODE";
  /// port for the master node
  public static final int SERVER_PORT = 2000;
  /// List of the nodes
  protected Map<String, AbstractNode> nodes;
  /// Server to create
  protected UnicastRemoteObject server;
  /// Registry of remote objects
  protected Registry directory;

  /**
   * Create a new master node
   * @throws RemoteException
   */
  protected NodeMaster() throws NodeAlreadyBoundException
  {
    nodes = new HashMap<String, AbstractNode>();
    // make the server reachable
    try
    {
      NodeMaster mn = (NodeMaster) UnicastRemoteObject.exportObject( this, 0 );
      //server.exportObject( this );
      directory = LocateRegistry.createRegistry( SERVER_PORT );
      directory.bind( SERVER_NAME, this );
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
      throw new NodeAlreadyBoundException( e );
    }
  }

  @Override
  public void register( AbstractNode node ) throws NodeAlreadyBoundException
  {
    if( nodes.containsKey( node.getName() ) )
    {
      throw new NodeAlreadyBoundException( "A node is already register under this name" );
    }
    else {
      nodes.put( node.getName(), node );
    }
  }

  @Override
  public void unregister( String nodeName )
  {
    AbstractNode n = nodes.remove( nodeName );
    if( null == n )
    {
      System.err.println( "No node registered for " + nodeName );
    }
  }

  @Override
  public Collection<String> getRegisteredNodes()
  {
    return nodes.keySet();
  }

  @Override
  public AbstractNode getRemoteNode( String name ) throws UnkownNodeException
  {
    if( nodes.containsKey( name ) )
    {
      return nodes.get( name );
    }
    else {
      throw new UnkownNodeException( "No node register under " + name );
    }
  }

  public static void main(String args[]) throws NodeAlreadyBoundException
  {
    NodeMaster master = new NodeMaster();
  }
}

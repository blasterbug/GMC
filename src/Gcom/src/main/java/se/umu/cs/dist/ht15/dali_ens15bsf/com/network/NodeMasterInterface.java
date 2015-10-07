package se.umu.cs.dist.ht15.dali_ens15bsf.com.network;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;

/**
 * Created by ens15bsf on 2015-10-06.
 */
public interface NodeMasterInterface extends Remote
{
  /**
   * Register a new node
   * @param node Node to register
   * @throws NodeAlreadyBoundException Throws this an exception when node name is already used
   */
  public void register( AbstractNode node ) throws NodeAlreadyBoundException, RemoteException;

  /**
   * Remove a node from the register
   * @param nodeName Name used to register the node to remove
   */
  public void unregister( String nodeName ) throws RemoteException;

  /**
   * Get a names list of the registered nodes
   * @return Collection containing a list of name of the registered nodes
   */
  public LinkedList<String> getRegisteredNodes() throws RemoteException;

  /**
   * Get the remote object for a given node
   * @param name Name of the node
   * @return Node registered under the given name
   * @throws UnkownNodeException Exception thrown when no node are register under the given name
   */
  public AbstractNode getRemoteNode( String name ) throws UnkownNodeException, RemoteException;
}

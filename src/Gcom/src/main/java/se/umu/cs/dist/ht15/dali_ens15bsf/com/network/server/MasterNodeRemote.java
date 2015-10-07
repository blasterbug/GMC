package se.umu.cs.dist.ht15.dali_ens15bsf.com.network.server;

import se.umu.cs.dist.ht15.dali_ens15bsf.com.network.NodeAlreadyBoundException;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.network.NodeRemote;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.network.UnkownNodeException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;

/**
 * Created by ens15bsf on 2015-10-06.
 */
interface MasterNodeRemote extends Remote
{
  /**
   * Register a new node
   * @param node Node to register
   * @throws se.umu.cs.dist.ht15.dali_ens15bsf.com.network.NodeAlreadyBoundException Throws this an exception when node name is already used
   */
  public void register( NodeRemote node ) throws NodeAlreadyBoundException, RemoteException;

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
   * @throws se.umu.cs.dist.ht15.dali_ens15bsf.com.network.UnkownNodeException Exception thrown when no node are register under the given name
   */
  public NodeRemote getRemoteNode( String name ) throws UnkownNodeException, RemoteException;
}

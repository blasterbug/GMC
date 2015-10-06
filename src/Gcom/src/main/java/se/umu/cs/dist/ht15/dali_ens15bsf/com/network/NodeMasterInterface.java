package se.umu.cs.dist.ht15.dali_ens15bsf.com.network;

import java.io.Serializable;
import java.rmi.Remote;
import java.util.Collection;

/**
 * Created by ens15bsf on 2015-10-06.
 */
public interface NodeMasterInterface extends Serializable, Remote
{
  /**
   * Register a new node
   * @param node Node to register
   * @throws NodeAlreadyBoundException Throws this an exception when node name is already used
   */
  public void register( AbstractNode node ) throws NodeAlreadyBoundException;

  /**
   * Remove a node from the register
   * @param nodeName Name used to register the node to remove
   */
  public void unregister( String nodeName );

  /**
   * Get a names list of the registered nodes
   * @return Collection containing a list of name of the registered nodes
   */
  public Collection<String> getRegisteredNodes();

  /**
   * Get the remote object for a given node
   * @param name Name of the node
   * @return Node registered under the given name
   * @throws UnkownNodeException Exception thrown when no node are register under the given name
   */
  public AbstractNode getRemoteNode( String name ) throws UnkownNodeException;
}

package se.umu.cs.dist.ht15.dali_ens15bsf.com;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by ens15bsf on 2015-10-12.
 * Define a strategy for several type of multicast process
 *
 * @serial
 */
public abstract class MulticastStrategy implements Serializable
{

  private static final long serialVersionUID = 5225746892740659055L;
  protected RemoteMember owner;
  protected ArrayList<RemoteMember> view;
  protected ArrayList<RemoteMember> unreachableMembers;

  public MulticastStrategy ()
  {
    view = new ArrayList<RemoteMember>();
  }

  /**
   * Strategy to multicast a message
   *
   * @param msg   Message to send
   * @param group Group to send the message
   * @throws UnreachableRemoteObjectException
   */
  public abstract void send ( CommMessage msg, Collection<RemoteMember> group ) throws UnreachableRemoteObjectException;

  /**
   * receive a message regarding the used strategy
   *
   * @param msg Incoming message
   * @throws java.rmi.RemoteException
   * @throws se.umu.cs.dist.ht15.dali_ens15bsf.com.UnreachableRemoteObjectException
   */
  public abstract void receive ( CommMessage msg ) throws RemoteException, UnreachableRemoteObjectException;

  /**
   * Choose a remote member for the strategy
   *
   * @param owner Remote member who uses the strategy
   */
  public void setOwner ( RemoteMember owner )
  {
    this.owner = owner;
  }

  /**
   * get the list of the unreachable remote members,
   * the list is empty after each call
   *
   * @return list of all remote members which thrown an exception
   */
  public Collection<RemoteMember> getUnreachableMembers ()
  {
    ArrayList<RemoteMember> tmp = new ArrayList<RemoteMember>( unreachableMembers );
    unreachableMembers.clear();
    return tmp;
  }
}

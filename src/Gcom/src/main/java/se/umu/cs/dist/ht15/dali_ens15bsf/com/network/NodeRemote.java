package se.umu.cs.dist.ht15.dali_ens15bsf.com.network;

import se.umu.cs.dist.ht15.dali_ens15bsf.com.CommMessage;

import java.rmi.RemoteException;

/**
 * Created by ens15bsf on 2015-10-07.
 */
public interface NodeRemote
{
  /**
   * What is the name of the node ?
   * @return Name of the node in a String
   * @throws RemoteException
   */
  public String getName() throws RemoteException;

  /**
   * Receive a Communication message from another node
   * @param msg Message to receive
   * @throws RemoteException
   */
  public void deliver( CommMessage msg ) throws RemoteException;

  /**
   * Add a member in the node's network representation
   * @param newM New member which joins the network
   * @throws RemoteException
   */
  public void addMember( NodeRemote newM ) throws RemoteException;

  /**
   * Remove a member in the node's network representation
   * @param oldM member to remove.
   * @throws RemoteException
   */
  public void removeMember( NodeRemote oldM ) throws RemoteException;
}

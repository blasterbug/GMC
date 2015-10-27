package se.umu.cs.dist.ht15.dali_ens15bsf.com;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import se.umu.cs.dist.ht15.dali_ens15bsf.groupmanagement.Member;

/**
 * Created by ens15bsf on 2015-10-08.
 * Define an interface for the Member object which are shared throw Java RMI API
 */
public interface RemoteMember extends Remote, Serializable
{

  /**
   * Receive a Communication message from another node
   *
   * @param msg Message to receive
   * @throws RemoteException
   */
  public void deliver ( CommMessage msg ) throws RemoteException;

  /**
   * Join a group
   *
   * @param newM    Member to add in the group
   * @param groupID Group name to join
   * @throws RemoteException
   */
  public void join ( RemoteMember newM, String groupID ) throws RemoteException;


  public String getId() throws RemoteException;

  public void addToView(RemoteMember m, String id) throws RemoteException;
}

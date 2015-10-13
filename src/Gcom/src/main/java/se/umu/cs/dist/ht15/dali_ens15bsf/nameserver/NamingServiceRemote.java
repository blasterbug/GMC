package se.umu.cs.dist.ht15.dali_ens15bsf.nameserver;

import se.umu.cs.dist.ht15.dali_ens15bsf.com.RemoteMember;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;

/**
 * Created by ens15bsf on 2015-10-06.
 */
public interface NamingServiceRemote extends Remote
{
  /**
   * Register a new member to a group
   *
   * @param groupName Name of the group to join
   * @param m         Member which wishes to join the group
   * @return Leader of the group
   */
  public RemoteMember joinGroup ( String groupName, RemoteMember m ) throws RemoteException;

  /**
   * Get a names list of the registered groups
   *
   * @return Collection containing registered groups
   */
  public LinkedList<String> getGroups () throws RemoteException;

  /**
   * Get the leader of a given group
   *
   * @param groupName Name of group
   * @return leader for the given group
   */
  public RemoteMember getLeader ( String groupName ) throws RemoteException;
}

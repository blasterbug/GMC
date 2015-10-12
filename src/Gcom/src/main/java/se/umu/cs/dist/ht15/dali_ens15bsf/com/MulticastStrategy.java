package se.umu.cs.dist.ht15.dali_ens15bsf.com;

import java.rmi.RemoteException;
import java.util.Collection;

/**
 * Created by ens15bsf on 2015-10-12.
 * Define a strategy for several type of multicast process
 */
public abstract class MulticastStrategy
{

  protected MemberRemote owner;
  protected Collection<MemberRemote> view;

  /**
   * Strategy to multicast a message
   *
   * @param msg   Message to send
   * @param group Group to send the message
   * @throws RemoteException
   */
  public abstract void send ( CommMessage msg, Collection<MemberRemote> group ) throws RemoteException;

  /**
   * receive a message regarding the used strategy
   *
   * @param msg Incoming message
   * @throws RemoteException
   */
  public abstract void receive ( CommMessage msg ) throws RemoteException;
}

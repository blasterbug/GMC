package Gcom.src.main.java.se.umu.cs.dist.ht15.dali_ens15bsf.com;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by ens15bsf on 2015-10-05.
 */
public class RMIService extends UnicastRemoteObject implements Remote, Serializable
{
  /**
   * @see java.rmi.server.UnicastRemoteObject
   */
  protected RMIService ( int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf ) throws RemoteException
  {
    super( port, csf, ssf );
  }

  /**
   * @see java.rmi.server.UnicastRemoteObject
   */
  protected RMIService () throws RemoteException
  {
  }

  /**
   * @see java.rmi.server.UnicastRemoteObject
   */
  protected RMIService ( int port ) throws RemoteException
  {
    super( port );
  }

}

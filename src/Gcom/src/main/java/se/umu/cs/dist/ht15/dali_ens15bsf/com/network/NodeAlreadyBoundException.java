package se.umu.cs.dist.ht15.dali_ens15bsf.com.network;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * Created by ens15bsf on 2015-10-06.
 * @serial
 */
public class NodeAlreadyBoundException extends RemoteException implements Serializable
{

  private static final long serialVersionUID = -7887953621196786974L;

  public NodeAlreadyBoundException ()
  {
  }

  public NodeAlreadyBoundException ( String s )
  {
    super( s );
  }

  public NodeAlreadyBoundException ( String s, Throwable cause )
  {
    super( s, cause );
  }
}

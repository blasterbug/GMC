package se.umu.cs.dist.ht15.dali_ens15bsf.com.network;

import java.io.Serializable;

/**
 * Created by ens15bsf on 2015-10-06.
 */
public class NodeAlreadyBoundException extends Exception implements Serializable
{
  public NodeAlreadyBoundException ( String message )
  {
    super( message );
  }

  public NodeAlreadyBoundException ()
  {
  }

  public NodeAlreadyBoundException ( Throwable cause )
  {
    super( cause );
  }

  public NodeAlreadyBoundException ( String message, Throwable cause )
  {
    super( message, cause );
  }

  public NodeAlreadyBoundException ( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace )
  {
    super( message, cause, enableSuppression, writableStackTrace );
  }
}

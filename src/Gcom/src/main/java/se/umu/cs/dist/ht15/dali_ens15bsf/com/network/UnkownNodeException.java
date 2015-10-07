package se.umu.cs.dist.ht15.dali_ens15bsf.com.network;

import java.io.Serializable;

/**
 * Created by ens15bsf on 2015-10-06.
 * @serial
 */
public class UnkownNodeException extends Exception implements Serializable
{

  private static final long serialVersionUID = 2479035822473468514L;

  public UnkownNodeException ()
  {
  }

  public UnkownNodeException ( String message )
  {
    super( message );
  }

  public UnkownNodeException ( String message, Throwable cause )
  {
    super( message, cause );
  }

  public UnkownNodeException ( Throwable cause )
  {
    super( cause );
  }

  public UnkownNodeException ( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace )
  {
    super( message, cause, enableSuppression, writableStackTrace );
  }
}

package se.umu.cs.dist.ht15.dali_ens15bsf.com;

/**
 * Created by ens15bsf on 2015-10-13.
 */
public class UnreachableRemoteObjectException extends Exception
{
  public UnreachableRemoteObjectException ()
  {
  }

  public UnreachableRemoteObjectException ( String message )
  {
    super( message );
  }

  public UnreachableRemoteObjectException ( String message, Throwable cause )
  {
    super( message, cause );
  }

  public UnreachableRemoteObjectException ( Throwable cause )
  {
    super( cause );
  }

  public UnreachableRemoteObjectException ( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace )
  {
    super( message, cause, enableSuppression, writableStackTrace );
  }
}

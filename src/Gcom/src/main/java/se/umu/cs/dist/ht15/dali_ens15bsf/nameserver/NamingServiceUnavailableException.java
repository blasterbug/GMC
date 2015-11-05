package se.umu.cs.dist.ht15.dali_ens15bsf.nameserver;

/**
 * Created by ens15bsf on 2015-11-05.
 */
public class NamingServiceUnavailableException extends Exception

{
  public NamingServiceUnavailableException ()
  {
  }

  public NamingServiceUnavailableException ( String message )
  {
    super( message );
  }

  public NamingServiceUnavailableException ( String message, Throwable cause )
  {
    super( message, cause );
  }

  public NamingServiceUnavailableException ( Throwable cause )
  {
    super( cause );
  }

  public NamingServiceUnavailableException ( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace )
  {
    super( message, cause, enableSuppression, writableStackTrace );
  }
}

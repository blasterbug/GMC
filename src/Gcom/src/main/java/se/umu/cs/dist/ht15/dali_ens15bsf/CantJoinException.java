package se.umu.cs.dist.ht15.dali_ens15bsf;

/**
 * Created by ens15bsf on 2015-11-06.
 */
public class CantJoinException extends Exception
{
  public CantJoinException ()
  {
    super();
  }

  public CantJoinException ( String message )
  {
    super( message );
  }

  public CantJoinException ( String message, Throwable cause )
  {
    super( message, cause );
  }

  public CantJoinException ( Throwable cause )
  {
    super( cause );
  }

  protected CantJoinException ( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace )
  {
    super( message, cause, enableSuppression, writableStackTrace );
  }
}

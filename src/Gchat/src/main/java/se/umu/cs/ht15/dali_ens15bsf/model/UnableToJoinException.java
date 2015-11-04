package se.umu.cs.ht15.dali_ens15bsf.model;

/**
 * Created by ens15bsf on 2015-10-29.
 */
public class UnableToJoinException extends Exception

{
  public UnableToJoinException ( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace )
  {
    super( message, cause, enableSuppression, writableStackTrace );
  }

  public UnableToJoinException ()
  {
  }

  public UnableToJoinException ( String message )
  {
    super( message );
  }

  public UnableToJoinException ( String message, Throwable cause )
  {
    super( message, cause );
  }

  public UnableToJoinException ( Throwable cause )
  {
    super( cause );
  }
}

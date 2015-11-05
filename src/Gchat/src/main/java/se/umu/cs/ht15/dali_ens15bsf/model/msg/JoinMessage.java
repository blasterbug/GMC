package se.umu.cs.ht15.dali_ens15bsf.model.msg;

/**
 * Created by ens15bsf on 2015-11-05.
 */
public class JoinMessage extends GMessage
{
  public JoinMessage( String user )
  {
    super( user, user + "wants to join" );
  }

  @Override
  public boolean isJoinMessage ()
  {
    return true;
  }
}

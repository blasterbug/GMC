package se.umu.cs.ht15.dali_ens15bsf.model.msg;

import java.io.Serializable;

/**
 * Created by ens15bsf on 2015-11-05.
 */
public class GJoinMessage extends GMessage implements Serializable
{
  private static final long serialVersionUID = -7365688800440580064L;

  public GJoinMessage ( String user, String group )
  {
    super( user, group );
  }

  @Override
  public boolean isJoinMessage ()
  {
    return true;
  }

  public String toString()
  {
    return author + " wants to join " + content;
  }
}

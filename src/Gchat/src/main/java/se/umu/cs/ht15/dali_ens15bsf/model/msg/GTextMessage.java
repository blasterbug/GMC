package se.umu.cs.ht15.dali_ens15bsf.model.msg;

import java.io.Serializable;

/**
 * Created by ens15bsf on 2015-11-05.
 */
public class GTextMessage extends GMessage implements Serializable
{
  private static final long serialVersionUID = -6224071543857161546L;

  public GTextMessage ( String from, String content )
  {
    super( from, content );
  }

  @Override
  public boolean isTextMessage ()
  {
    return true;
  }
}

package se.umu.cs.ht15.dali_ens15bsf.model.msg;

/**
 * Created by ens15bsf on 2015-11-05.
 */
public class TextMessage extends GMessage
{
  public TextMessage( String from, String content )
  {
    super( from, content );
  }

  @Override
  public boolean isTextMessage ()
  {
    return true;
  }
}

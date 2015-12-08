package se.umu.cs.dist.ht15.dali_ens15bsf.com.debug;

import se.umu.cs.dist.ht15.dali_ens15bsf.com.ComMessage;
import se.umu.cs.dist.ht15.dali_ens15bsf.debug.TimeUtils;

import java.util.Date;

/**
 * Created by ens15bsf on 04/12/15.
 */
public class DelayedComMessage extends ComMessage<ComMessage>
{
  private long timestamp;

  public DelayedComMessage ( ComMessage message  )
  {
    super( message );
    timestamp = new Date().getTime();
  }

  public ComMessage getContent()
  {
    return super.content;
  }

  public long getTime()
  {
    return timestamp;
  }

  /**
   * Get a string representation of the message
   *
   * @return String representation of the message
   */
  @Override
  public String toString ()
  {
    return "(" + TimeUtils.getTime( timestamp ) + ") " + super.toString();
  }
}

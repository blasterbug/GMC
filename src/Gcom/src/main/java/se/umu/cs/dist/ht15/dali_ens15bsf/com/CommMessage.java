package se.umu.cs.dist.ht15.dali_ens15bsf.com;

/**
 * Created by ens15bsf on 2015-10-07.
 */
public class CommMessage
{
  protected final Object content;

  public CommMessage( Object content )
  {
    this.content = content;
  }

  public String toString()
  {
    return content.toString();
  }
}

package se.umu.cs.dist.ht15.dali_ens15bsf.com;

import java.io.Serializable;

/**
 * Created by ens15bsf on 2015-10-07.
 * Define Message type exchange between Members
 */
public class CommMessage<T> implements Serializable
{
  private static final long serialVersionUID = 5554064569627466554L;
  protected final T content;
  protected final MemberRemote source;

  /**
   * Create a new CommMessage
   *
   * @param content Content of the message
   * @param sender  who send the message
   */
  public CommMessage ( T content, MemberRemote sender )
  {
    this.content = content;
    source = sender;
  }

  /**
   * Get a string representation of the message
   *
   * @return String representation of the message
   */
  public String toString ()
  {
    return source.toString() + " send " + content.toString();
  }

  /**
   * What is the content of the message ?
   *
   * @return Content of the message
   */
  public T getContent ()
  {
    return content;
  }

  /**
   * Who send the message ?
   *
   * @return Remote object of the sender
   */
  public MemberRemote getSource ()
  {
    return source;
  }
}

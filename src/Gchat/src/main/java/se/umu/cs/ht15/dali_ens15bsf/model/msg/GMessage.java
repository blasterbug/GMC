package se.umu.cs.ht15.dali_ens15bsf.model.msg;

import java.io.Serializable;

/**
 * Created by ens15bsf on 2015-11-05.
 * Describe messages exchanges by Gchat apps
 */
public abstract class GMessage implements Serializable
{
  private static final long serialVersionUID = 4322512454333396129L;
  protected String author;
  protected String content;

  protected GMessage ( String user, String content )
  {
    author = user;
    this.content = content;
  }

  /**
   * Get the content of the message
   *
   * @return Content of the message
   */
  public String getContent ()
  {
    return content;
  }

  /**
   * Get the message author
   *
   * @return the author of the message
   */
  public String getAuthor ()
  {
    return author;
  }

  /**
   * Is the message a join message ?
   *
   * @return
   */
  public boolean isJoinMessage ()
  {
    return false;
  }

  /**
   * Is the message a quit message ?
   *
   * @return
   */
  public boolean isQuitMessage ()
  {
    return false;
  }

  /**
   * Is the message a text message ?
   *
   * @return
   */
  public boolean isTextMessage ()
  {
    return false;
  }

  public String toString()
  {
    return author + " : " + content;
  }
}

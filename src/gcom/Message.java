package gcom;

/**
 * Messages exchqnge by the GCOM module
 * Created by ens15bsf on 2015-09-25.
 */
public class Message
{
  // MIME type of the message
  protected String type;
  // definetly not the right type for store photos or whatever
  protected String content;

  /**
   * Contructor for messages
   * @param content Content of the message
   * @param type Type of the message, should respect MIME type
   */
  public Message(String content, String type)
  {
    this.content = content;
    this.type = type;
  }

  /**
   * Constructor without a given type
   * @param content content of the message
   */
  public Message( String content )
  {
    this( content, "NONE");
  }

  /**
   * What is the message about ?
   * @return String representation of the content
   */
  public String getString()
  {
    return content;
  }

  /**
   * What is the type of the message ?
   * @
   */
  public String getType()
  {
    return type;
  }
}

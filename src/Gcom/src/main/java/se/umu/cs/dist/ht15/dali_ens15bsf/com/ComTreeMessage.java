package se.umu.cs.dist.ht15.dali_ens15bsf.com;

import java.io.Serializable;
import java.util.Vector;

/**
 * Created by ens15bsf on 09/12/15.
 */
public class ComTreeMessage<T extends Serializable> extends ComMessage
{
  Vector<RemoteMember> tree;
  int rootIndex;

  /**
   * Create a new CommMessage
   *
   * @param content Content of the message
   */
  public ComTreeMessage ( T content )
  {
    super( content );
    tree = new Vector<RemoteMember>();
    rootIndex = -1;
  }

  public int getRoot()
  {
    return rootIndex;
  }

}

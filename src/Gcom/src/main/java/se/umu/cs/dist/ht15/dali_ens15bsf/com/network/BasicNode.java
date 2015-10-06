package se.umu.cs.dist.ht15.dali_ens15bsf.com.network;

import se.umu.cs.dist.ht15.dali_ens15bsf.com.CommManager;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.CommMessage;

/**
 * Created by ens15bsf on 2015-10-06.
 */
public class BasicNode extends AbstractNode
{
  /**
   * Create a new node
   *
   * @param owner Communication manager owning the node
   */
  public BasicNode ( CommManager owner )
  {
    super( owner );
  }

  /**
   * Send a message by multicast to the other nodes
   *
   * @param msg Message to multicast
   */
  @Override
  public void post ( CommMessage msg )
  {
    for( AbstractNode node : network )
    {
      node.deliver( msg );
    }
  }
}

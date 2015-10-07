package se.umu.cs.dist.ht15.dali_ens15bsf.com.network;

import se.umu.cs.dist.ht15.dali_ens15bsf.com.CommManager;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.CommMessage;

import java.rmi.RemoteException;
import java.util.LinkedList;

/**
 * Created by ens15bsf on 2015-10-06.
 *
 * @serial
 */
public class BasicNode extends Node
{
  private static final long serialVersionUID = -8662641569121124733L;

  /**
   * Create a new node
   *
   * @param owner Communication manager owning the node
   * @throws RemoteException
   */
  public BasicNode ( CommManager owner ) throws RemoteException
  {
    super( owner );
  }

  /**
   * Send a message by multicast to the other nodes
   *
   * @param msg Message to multicast
   * @throws UnreachableNodesException
   */
  @Override
  public void post ( CommMessage msg ) throws UnreachableNodesException
  {
    // create the message to send
    NodeMessage toSend = new NodeMessage( msg, this );
    // broadcast it
    LinkedList<String> unreachableNodes = new LinkedList<String>();
    for ( String nodeID : network.keySet() )
    {
      try
      {
        network.get( nodeID ).deliver( toSend );
      } catch ( RemoteException e )
      {
        unreachableNodes.add( nodeID );
      }
    }
    if ( !( unreachableNodes.isEmpty() ) )
    {
      throw new UnreachableNodesException( unreachableNodes );
    }
  }
}

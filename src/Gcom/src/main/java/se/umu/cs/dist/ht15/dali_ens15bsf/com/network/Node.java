package se.umu.cs.dist.ht15.dali_ens15bsf.com.network;

import se.umu.cs.dist.ht15.dali_ens15bsf.com.CommManager;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.CommMessage;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by ens15bsf on 2015-10-06.
 * A node is a remote object used by java RMI
 * @serial
 */
public abstract class Node implements Serializable, NodeRemote
{

  private static final long serialVersionUID = 6305983442326469023L;
  protected String name;
  /**
   * list of known nodes
   */
  protected Map<String, NodeRemote> network;
  /**
   * Communication manager for the node
   */
  protected CommManager owner;

  /**
   * Create a new node
   * @param owner Communication manager owning the node
   */
  public Node( CommManager owner )
  {
    /// TODO: Use socket instead
    name = String.valueOf( new Random().nextInt() );
    this.owner = owner;
    network = new HashMap<String, NodeRemote>();
    /*
    try
    {
      Registry rgt = LocateRegistry.getRegistry( LocateRegistry.g );
      rgt.bind( );
    } catch ( RemoteException e )
    {
      System.err.println( e.getCause() );
    }
    */
  }


  @Override
  public String getName() throws RemoteException
  {
    return name;
  }

  /**
   * Send a message by multicast to the other nodes
   * @param msg Message to multicast
   * @throws UnreachableNodesException
   */
  public abstract void post( CommMessage msg ) throws UnreachableNodesException;

  @Override
  public void deliver( NodeMessage msg ) throws RemoteException
  {
    owner.receive( msg.getContent() );
  }

  @Override
  public void addMember( NodeRemote newM ) throws RemoteException
  {
    network.put( newM.getName(), newM );
  }

  @Override
  public void removeMember( NodeRemote oldM ) throws RemoteException
  {
    network.remove( oldM.getName() );
  }
}

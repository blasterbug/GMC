package se.umu.cs.dist.ht15.dali_ens15bsf.com.network;

import se.umu.cs.dist.ht15.dali_ens15bsf.com.CommManager;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.CommMessage;

import java.io.Serializable;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteObject;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by ens15bsf on 2015-10-06.
 * A node is a remote object used by java RMI
 */
public abstract class AbstractNode extends RemoteObject implements Serializable
{
  protected String name;
  /**
   * list of known nodes
   */
  protected Collection<AbstractNode> network;
  /**
   * Communication manager for the node
   */
  protected CommManager owner;

  /**
   * Create a new node
   * @param owner Communication manager owning the node
   */
  public AbstractNode( CommManager owner )
  {
    /// TODO: Use socket instead
    name = String.valueOf( new Random().nextInt() );
    this.owner = owner;
    network = new LinkedList<AbstractNode>( );
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

  /**
   * What is the name of the node ?
   * @return Name of the node in a String
   */
  public String getName()
  {
    return name;
  }
  /**
   * Send a message by multicast to the other nodes
   * @param msg Message to multicast
   */
  public abstract void post( CommMessage msg );

  /**
   * Receive a Communication message from another node
   * @param msg Message to receive
   */
  public void deliver( CommMessage msg )
  {

    owner.receive( msg );
  }

  public void addMember( AbstractNode newM )
  {
    network.add( newM );
  }

  public void removeMember( AbstractNode newM )
  {
    network.remove( newM );
  }
}

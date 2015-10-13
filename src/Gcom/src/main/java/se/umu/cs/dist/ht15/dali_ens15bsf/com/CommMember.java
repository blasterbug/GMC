package se.umu.cs.dist.ht15.dali_ens15bsf.com;

import se.umu.cs.dist.ht15.dali_ens15bsf.groupmanagement.Member;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by ens15bsf on 2015-10-08.
 */
public class CommMember implements RemoteMember, Serializable
{
  private static final long serialVersionUID = 4672654439762386594L;
  protected ArrayList<RemoteMember> group;
  protected Member owner;
  protected MulticastStrategy commStrategy;

  /**
   * Create a new node
   *
   * @param owner Communication manager owning the node
   */
  public CommMember ( Member owner, String groupID, MulticastStrategy multicastStrategy )
  {
    this.owner = owner;
    group = new ArrayList<RemoteMember>();
    commStrategy = multicastStrategy;
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
   * Send a message by multicast to the other nodes
   *
   * @param msg   Message to multicast
   * @param group Group to send the message
   * @throws java.rmi.RemoteException
   */
  public void post ( CommMessage msg, Collection<RemoteMember> group ) throws RemoteException
  {
    commStrategy.send( msg, group );
  }

  /**
   * Receive a Communication message from another node
   *
   * @param msg Message to receive
   * @throws java.rmi.RemoteException
   */
  @Override
  public void deliver ( CommMessage msg ) throws RemoteException
  {
    commStrategy.receive( msg );
  }

  /**
   * Join a group
   *
   * @param newM    Member to add in the group
   * @param groupID Group name to join
   * @throws java.rmi.RemoteException
   */
  @Override
  public void join ( RemoteMember newM, String groupID ) throws RemoteException
  {
    owner.join( newM, groupID );
  }

  @Override
  public String getMemberID () throws RemoteException
  {
    return owner.toString();
  }
}

package se.umu.cs.dist.ht15.dali_ens15bsf.com;

import se.umu.cs.dist.ht15.dali_ens15bsf.Message;
import se.umu.cs.dist.ht15.dali_ens15bsf.groupmanagement.Member;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by ens15bsf on 2015-10-08.
 */
public abstract class CommMember implements MemberRemote, Serializable
{
  private static final long serialVersionUID = 4672654439762386594L;
  protected ArrayList<MemberRemote> group;
  protected Member owner;
  protected String groupID;

  /**
   * Create a new node
   *
   * @param owner Communication manager owning the node
   */
  public CommMember ( Member owner, String groupID )
  {
    this.owner = owner;
    this.groupID = groupID;
    group = new ArrayList<MemberRemote>();
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
   * @param msg Message to multicast
   * @throws java.rmi.RemoteException
   * @throws UnreachableMemberException
   */
  public abstract void post ( CommMessage msg ) throws RemoteException, UnreachableMemberException;

  /**
   * Receive a Communication message from another node
   *
   * @param msg Message to receive
   * @throws java.rmi.RemoteException
   */
  @Override
  public void deliver ( CommMessage msg ) throws RemoteException
  {
    // if the message is not from the same node
    if ( this.getMemberID() != msg.getSource().getMemberID() )
      owner.receiveMessage( (Message) msg.getContent() );
  }

  @Override
  public void join ( MemberRemote newM ) throws RemoteException
  {
    // TODO
  }

  @Override
  public String getMemberID () throws RemoteException
  {
    return owner.toString();
  }
}

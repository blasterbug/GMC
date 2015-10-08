package se.umu.cs.dist.ht15.dali_ens15bsf.com;

import se.umu.cs.dist.ht15.dali_ens15bsf.groupmanagement.Member;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * Created by ens15bsf on 2015-10-08.
 */
public class BasicReliableMember extends CommMember implements Serializable
{

  private static final long serialVersionUID = -679159579123158368L;

  /**
   * Create a new node
   *
   * @param owner   Communication manager owning the node
   * @param groupID group name fo the member
   */
  public BasicReliableMember ( Member owner, String groupID )
  {
    super( owner, groupID );
  }

  /**
   * Send a message by multicast to the other nodes
   *
   * @param msg Message to multicast
   */
  @Override
  public void post ( CommMessage msg ) throws RemoteException, UnreachableMemberException
  {
    // broadcast it
    String unreachableNodes = "";
    for ( MemberRemote mem : group )
    {
      try
      {
        mem.deliver( msg );
      } catch ( RemoteException e )
      {
        unreachableNodes += mem.toString() + ", ";
      }
    }
    if ( !( unreachableNodes.isEmpty() ) )
    {
      throw new UnreachableMemberException( unreachableNodes );
    }
  }

}

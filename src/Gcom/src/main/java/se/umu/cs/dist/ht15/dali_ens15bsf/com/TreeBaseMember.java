package se.umu.cs.dist.ht15.dali_ens15bsf.com;

import se.umu.cs.dist.ht15.dali_ens15bsf.groupmanagement.Member;

import java.rmi.RemoteException;

/**
 * Created by ens15bsf on 2015-10-08.
 */
public class TreeBaseMember extends CommMember
{
  /**
   * Create a new node
   *
   * @param owner   Communication manager owning the node
   * @param groupID
   */
  public TreeBaseMember ( Member owner, String groupID )
  {
    super( owner, groupID );
  }

  /**
   * Send a message by multicast to the other nodes
   *
   * @param msg Message to multicast
   * @throws java.rmi.RemoteException
   * @throws se.umu.cs.dist.ht15.dali_ens15bsf.com.UnreachableMemberException
   */
  @Override
  public void post ( CommMessage msg ) throws RemoteException, UnreachableMemberException
  {
    int idx = group.indexOf( this );
    // send message to the "left child"
    try
    {
      group.get( idx - 1 ).deliver( msg );
    } catch ( IndexOutOfBoundsException e )
    {
    } // do nothing
    // send message to the left child
    try
    {
      group.get( idx + 1 ).deliver( msg );
    } catch ( IndexOutOfBoundsException e )
    {
    }  // do nothing
  }
}

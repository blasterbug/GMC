package se.umu.cs.dist.ht15.dali_ens15bsf.com;

import java.rmi.RemoteException;

/**
 * Created by ens15bsf on 07/12/15.
 */
public class ComMemberDebug extends ComMember
{
  /**
   * Create a new node
   *
   * @param strategy Strategy to use for multicasting messages
   * @param mbr
   */
  public ComMemberDebug ( MulticastStrategy strategy, ComObserver mbr )
  {
    super( strategy, mbr );
  }

  /**
   * Receive a Communication message from another node
   *
   * @param msg Message to receive
   * @throws java.rmi.RemoteException
   */
  @Override
  public void deliver ( ComMessage msg ) throws RemoteException
  {
    //super.deliver( msg );
  }
}

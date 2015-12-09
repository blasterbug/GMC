package se.umu.cs.dist.ht15.dali_ens15bsf.com;

import java.rmi.RemoteException;
import java.util.Collection;

/**
 * Created by ens15bsf on 2015-10-08.
 * Tree based multicast, rather than sending to everyone the message,
 * send only the message to our "neighbours"
 */
public class TreeBaseMulticast extends MulticastStrategy<ComTreeMessage>
{

  /**
   * Create a new node
   */
  public TreeBaseMulticast ()
  {
    super();
  }

  /**
   * Strategy to multicast a message
   *
   * @param msg   Message to send
   * @param group Group to send the message
   * @throws UnreachableRemoteObjectException
   */
  @Override
  public void send ( ComTreeMessage msg, Collection<RemoteMember> group ) throws UnreachableRemoteObjectException
  {
    updatePath( msg );
    boolean throwException = false;
    // if we are the root
    if ( msg.tree.isEmpty() )
    {
      // update message source
      msg.source = owner;
      // add the group to the tree
      for ( RemoteMember member : group )
        msg.tree.add( member );
      view = msg.tree;
      // update root index
      int index = -1;
      String rootID;
      for ( RemoteMember node : view )
      {
        try
        {
          rootID = node.getId();
          index++;
          if ( owner.getId().equals( rootID ) )
            msg.rootIndex = index;
        }
        catch ( RemoteException e )
        {
          throwException = true;
          unreachableMembers.add( node );
        }
      }
      if ( msg.rootIndex < 1 )
      {
        for ( RemoteMember node : view )
          try
          {
            msg.tree.remove( node );
            node.deliver( msg );
          }
          catch ( RemoteException e )
          {
            throwException = true;
            unreachableMembers.add( node );
          }
      }
      else
      {
        msg.tree.get( msg.rootIndex + 1 );
      }
    }
    if ( throwException )
      throw new UnreachableRemoteObjectException();
  }

  /**
   * receive a message regarding the used strategy
   *
   * @param msg Incoming message
   * @throws java.rmi.RemoteException
   * @throws UnreachableRemoteObjectException
   */
  @Override
  public void receive ( ComTreeMessage msg ) throws RemoteException, UnreachableRemoteObjectException
  {
    printPath( msg );
    owner.queue( msg );
  }
}

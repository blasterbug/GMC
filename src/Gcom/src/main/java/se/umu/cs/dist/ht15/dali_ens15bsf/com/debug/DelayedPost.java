package se.umu.cs.dist.ht15.dali_ens15bsf.com.debug;

import se.umu.cs.dist.ht15.dali_ens15bsf.com.ComMessage;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.RemoteMember;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.UnreachableRemoteObjectException;

import java.util.Collection;

/**
 * Created by ens15bsf on 2015-11-20.
 */
class DelayedPost implements Runnable
{
  private ComMemberDebug mbr;
  private ComMessage toSend;
  private Collection<RemoteMember> group;
  private long time;

  public DelayedPost( ComMemberDebug member, ComMessage msg, Collection<RemoteMember> view, long delayTime )
  {
    mbr = member;
    toSend = msg;
    group = view;
    time = delayTime;
  }

  public void runWithDelay() throws UnreachableRemoteObjectException
  {
    run();
    mbr.postDebug( toSend, group );
  }

  @Override
  public void run ()
  {
    try
    {
      Thread.sleep( time );
    }
    catch ( InterruptedException e )
    {
      e.printStackTrace();
    }
  }
}

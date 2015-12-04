package se.umu.cs.dist.ht15.dali_ens15bsf.com.debug;

import se.umu.cs.dist.ht15.dali_ens15bsf.com.ComMessage;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.RemoteMember;

import java.util.Collection;

/**
 * Created by ens15bsf on 04/12/15.
 */
public class DelayedComMessage<T>
{
  private Collection<RemoteMember> view;
  private ComMessage msg;

  public DelayedComMessage ( ComMessage message, Collection<RemoteMember> group  )
  {
    msg = message;
    view = group;
  }

  public ComMessage getContent()
  {
    return msg;
  }

  public Collection<RemoteMember> getView()
  {
    return view;
  }
}

package se.umu.cs.dist.ht15.dali_ens15bsf.com.debug;

import se.umu.cs.dist.ht15.dali_ens15bsf.com.ComMessage;

/**
 * Created by ens15bsf on 04/12/15.
 */
public interface ComMemberDebugObserver
{
  void notifyQueued ( int i, ComMessage msg );
}

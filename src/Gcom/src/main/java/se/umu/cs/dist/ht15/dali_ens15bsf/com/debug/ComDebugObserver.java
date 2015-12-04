package se.umu.cs.dist.ht15.dali_ens15bsf.com.debug;

import se.umu.cs.dist.ht15.dali_ens15bsf.com.ComMessage;

/**
 * Created by ens15bsf on 2015-11-04.
 */
public interface ComDebugObserver
{
  public void notifyIncomingComMessage( ComMessage msg );
  public void notifyOutgoingComMessage( ComMessage msg );
}

package se.umu.cs.dist.ht15.dali_ens15bsf.debug;

import java.io.Serializable;

/**
 * Created by ens15bsf on 2015-11-18.
 */
public interface GcomDebugObserver
{
  public void notifyOutgoingMessage ( Serializable msg );
  public void notifyIncomingMessage ( Serializable msg );
  public void notifyJoin( String groupID );
  public void notifyConnect();
}

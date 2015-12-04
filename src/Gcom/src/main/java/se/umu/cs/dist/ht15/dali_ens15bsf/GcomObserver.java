package se.umu.cs.dist.ht15.dali_ens15bsf;

import java.io.Serializable;

/**
 * Created by ens15bsf on 2015-11-05.
 *
 */
public interface GcomObserver<T extends Serializable>
{
  public void newMessage( Message<T> msg );
  public void join( String gid ) throws CantJoinException;
}

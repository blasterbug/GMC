package se.umu.cs.dist.ht15.dali_ens15bsf.com.debug;

/**
 * Created by ens15bsf on 04/12/15.
 */
public interface ComDebugObserver
{
  void notifyQueued ( int i, String msg );

  void notifyUnqueued ( int messageIndex );

  void notifyDropped( int message );
}

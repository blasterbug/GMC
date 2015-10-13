package se.umu.cs.dist.ht15.dali_ens15bsf.com;

/**
 * Created by ens15bsf on 2015-10-13.
 * Observer pattern for the communication layer in Gcom
 */
public interface ComObservable
{
  /**
   * Register a new observer to the observable object
   * @param ob observer to register
   */
  public void addObserver( ComObserver ob );
}

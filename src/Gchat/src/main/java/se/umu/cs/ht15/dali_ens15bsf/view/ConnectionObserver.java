package se.umu.cs.ht15.dali_ens15bsf.view;

/**
 * Created by ens15bsf on 2015-11-05.
 */
public interface ConnectionObserver
{

  /**
   * Get notified when a user is connected
   *
   * @param uid New user ID
   */
  public void connected ( String uid );

  /**
   * Get notified when the current is trying to join a group
   *
   * @param uid username
   */
  public void connecting ( String uid );

  /**
   * Get notified when an user get disconnected
   *
   * @param uid disconnected user ID
   */
  public void disconnected ( String uid );
}

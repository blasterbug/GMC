package se.umu.cs.dist.ht15.dali_ens15bsf.com;

/**
 * Created by ens15bsf on 2015-10-13.
 */
public interface ComObserver
{
  /**
   * Notify Observers when a new incoming message arrive
   * @param msg message to give to the Observer
   */
  public void notifyObservers( CommMessage msg );

  /**
   * Notify observer when new member want to join a group
   * @param member New member joining to the group
   * @param groupID Group name to join
   */
  public void notifyNewMember( RemoteMember member, String groupID );
}

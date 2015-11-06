package se.umu.cs.dist.ht15.dali_ens15bsf;


import se.umu.cs.dist.ht15.dali_ens15bsf.nameserver.NamingServiceUnavailableException;

import java.io.Serializable;

/**
 * Created by ens15bsf on 2015-11-05.
 */
public interface Gcom<T extends Serializable>
{
  /**
   * Connect the member to the NameServer, should be called first.
   */
  public void connect() throws NamingServiceUnavailableException;

  /**
   * Get the list of all available group
   * @return Array of the available groups name
   */
  public String[] getGroups();

  /**
   * Join a group of member
   * @param groupid name of the group to join
   */
  public void join( String groupid )  throws CantJoinException;

  /**
   * Send a message to the group
   * @param msg message to send
   */
  public void send( T msg );

  /**
   * Add an observer to be notified when new message arrived
   * @param obs Observer to notify
   */
  public void addObserver( GcomObserver obs );
}

package se.umu.cs.dist.ht15.dali_ens15bsf.com;

/**
 * Created by benjamin on 04/10/15.
 */
public interface CommunicationService
{
    /**
     * Send a message
     *
     * @param msg Message to send
     */
    public void send( CommMessage msg );

    /**
     * Register a new listener to be notify when new
     * messages arrived
     *
     * @param listener Listener to notify
     */
    public void register( CommListener listener );

    /**
     * Remove a listener
     * @param listener Listener to stop notifying
     */
    public void remove( CommListener listener );
}

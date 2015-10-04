package Gcom.src.main.java.se.umu.cs.dist.ht15.dali_ens15bsf.com;

import java.util.Collection;

/**
 * Created by benjamin on 04/10/15.
 */
public class CommManager implements CommunicationService
{
    /// Listeners to notify
    private Collection<CommListener> listeners;

    public CommManager()
    {

    }

    /**
     * Send a message
     * @param msg Message to send
     */
    public void post(CommMessage msg)
    {
    }

    /**
     * Register a new listener
     * @param listener Listener to notify
     */
    public void register( CommListener listener )
    {

    }

    /**
     * Remove a listeners from the listeners list
     * @param listener Listener to stop notifying
     */
    public void remove( CommListener listener )
    {

    }
}

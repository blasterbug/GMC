package Gcom.src.main.java.se.umu.cs.dist.ht15.dali_ens15bsf.com;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by benjamin on 04/10/15.
 */
public class CommManager implements CommunicationService
{
    /// Listeners to notify
    private Collection<CommListener> listeners;
    /**
     * Constructor
     */
    public CommManager()
    {
        listeners = new ArrayList<CommListener>( );
    }

    /**
     * Send a message
     * @param msg Message to send
     */
    public void post( CommMessage msg )
    {
    }

    /**
     * Queue messages to listeners
     * @param msg Message to queue
     */
    public void receive( CommMessage msg )
    {
        for( CommListener listener : listeners )
        {
            listener.queueCommMessage( msg );
        }
    }

    /**
     * Register a new listener
     * @param listener Listener to notify
     */
    public void register( CommListener listener )
    {
        listeners.add( listener );
    }

    /**
     * Remove a listeners from the listeners list
     * @param listener Listener to stop notifying
     */
    public void remove( CommListener listener )
    {
        listeners.remove( listener );
    }
}

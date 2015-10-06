package se.umu.cs.dist.ht15.dali_ens15bsf.com;

import se.umu.cs.dist.ht15.dali_ens15bsf.com.network.AbstractNode;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.network.BasicNode;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by benjamin on 04/10/15.
 * Remote object to send qnd receive messages by java RMI
 */
public class CommManager implements CommunicationService
{
    /// Listeners to notify
    private Collection<CommListener> listeners;
    private AbstractNode node;

    /**
     * Constructor
     */
    public CommManager()
    {
        listeners = new ArrayList<CommListener>( );
        node = new BasicNode( this );
    }

    /**
     * Send a message
     * @param msg Message to send
     */
    public void send( CommMessage msg )
    {
        node.post( msg );
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

package Gcom.src.main.java.se.umu.cs.dist.ht15.dali_ens15bsf.com;

/**
 * Created by benjamin on 04/10/15.
 *
 * Interface for CommListener
 *
 *
 */
public interface CommListener
{
    /**
     * Notify a listener when new messages are ready
     * to be processed.
     */
    public void deliver();
    /**
     * Add a message in the listener queue
     * @param msg Message to queue
     */
    public void queueCommMessage( CommMessage msg );
}

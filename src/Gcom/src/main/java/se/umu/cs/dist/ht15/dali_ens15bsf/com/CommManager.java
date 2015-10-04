package Gcom.src.main.java.se.umu.cs.dist.ht15.dali_ens15bsf.com;

import java.util.Collection;

/**
 * Created by benjamin on 04/10/15.
 */
public class CommManager implements CommunicationService
{
    private Collection<CommListener> listeners;
    
    public void post(CommMessage msg) {
    }

    public void register( CommListener listener )
    {

    }
    public void remove( CommListener listener )
    {

    }
}

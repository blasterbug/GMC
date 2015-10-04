package Gcom.src.main.java.se.umu.cs.dist.ht15.dali_ens15bsf.com;

/**
 * Created by benjamin on 04/10/15.
 */
public interface CommunicationService
{
    /**
     * 
     * @param msg
     */
    public void post( CommMessage msg );
    public void register( CommListener listener );
    public void remove( CommListener listener );
}

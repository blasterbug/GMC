package se.umu.cs.dist.ht15.dali_ens15bsf.com;

import java.io.Serializable;

/**
 * Created by benjamin on 04/10/15.
 *
 * CommunicationService Message
 *
 */
public class CommMessage implements Serializable
{
    protected Object content;

    /**
     * Create a new Communication Message
     * @param content Content of the nez message
     */
    public CommMessage( Object content )
    {}

    /**
     * What is the content of the message ?
     * @return Object representation of the content
     */
    public Object getContent()
    {
        return content;
    }
}

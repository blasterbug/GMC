package se.umu.cs.dist.ht15.dali_ens15bsf.com.network;

import se.umu.cs.dist.ht15.dali_ens15bsf.com.CommMessage;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.network.Node;

import java.io.Serializable;

/**
 * Created by benjamin on 04/10/15.
 *
 * CommunicationService Message
 *
 */
public class NodeMessage implements Serializable
{

    private static final long serialVersionUID = 4184259822781074649L;
    protected final CommMessage content;
    protected final Node source;

    /**
     * Create a new Communication Message
     * @param content Content of the nez message
     * @param sender Who sends the message
     */
    public NodeMessage( CommMessage content, Node sender )
    {
        source = sender;
        this.content = content;
    }

    /**
     * What is the content of the message ?
     * @return Object representation of the content
     */
    public CommMessage getContent()
    {
        return content;
    }

    /**
     * Who send the message ?
     * @return Node which send the message
     */
    public Node getSource ()
    {
        return source;
    }
}

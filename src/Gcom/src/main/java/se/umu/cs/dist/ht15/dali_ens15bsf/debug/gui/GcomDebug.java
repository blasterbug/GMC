package se.umu.cs.dist.ht15.dali_ens15bsf.debug.gui;

import se.umu.cs.dist.ht15.dali_ens15bsf.com.ComMessage;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.debug.ComDebugObserver;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ens15bsf on 2015-11-04.
 */
public class GcomDebug extends JFrame implements ComDebugObserver
{

  private List comIncomimgMsg;
  private List comOutgoingMsg;
  private TextArea delay;

  public GcomDebug(  )
  {

    // ######## COM MODULE DATA
    JPanel comStuff = new JPanel();
    GroupLayout comLayout = new GroupLayout( comStuff );
    comStuff.setLayout( comLayout );

    // ## Incomimg messages
    comIncomimgMsg = new List();
    comIncomimgMsg.setFocusable( false );
    JScrollPane comIMScroll = new JScrollPane( comIncomimgMsg );

    comOutgoingMsg = new List();


    // ######## ORDERING MODULE DATA
    //TODO
  }

  @Override
  public void notifyIncomingMessage ( ComMessage msg )
  {
    comIncomimgMsg.add( msg.toString() );
  }

  @Override
  public void notifyOutgoingMessage ( ComMessage msg )
  {
    comOutgoingMsg.add( msg.toString() );
  }
}

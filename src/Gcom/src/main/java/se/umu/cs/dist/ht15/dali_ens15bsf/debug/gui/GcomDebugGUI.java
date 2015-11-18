package se.umu.cs.dist.ht15.dali_ens15bsf.debug.gui;

import se.umu.cs.dist.ht15.dali_ens15bsf.Gcom;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.ComMessage;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.debug.ComDebugObserver;
import se.umu.cs.dist.ht15.dali_ens15bsf.GcomDebug;
import se.umu.cs.dist.ht15.dali_ens15bsf.debug.GcomDebugObserver;
import se.umu.cs.dist.ht15.dali_ens15bsf.nameserver.NamingServiceUnavailableException;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * Created by ens15bsf on 2015-11-04.
 */
public class GcomDebugGUI extends JFrame implements ComDebugObserver, GcomDebugObserver
{

  private List comIncomimgMsg;
  private List comOutgoingMsg;
  private List comInfo;
  private TextArea delay;

  public GcomDebugGUI ( Gcom model )
  {

    try
    {
      GcomDebug debugModel = new GcomDebug( model );
    }
    catch ( RemoteException | NamingServiceUnavailableException e )
    {
      e.printStackTrace();
      System.exit( 1 );
    }

    // ######## COM MODULE DATA
    JPanel comStuff = new JPanel();
    GroupLayout comLayout = new GroupLayout( comStuff );
    comStuff.setLayout( comLayout );

    // ## Incoming messages
    comIncomimgMsg = new List();
    comIncomimgMsg.setFocusable( false );
    JScrollPane comIMScroll = new JScrollPane( comIncomimgMsg );
    comIMScroll.setBorder( BorderFactory.createTitledBorder( "Incoming messages" ) );

    // ## Outgoing messages
    comOutgoingMsg = new List();
    comOutgoingMsg.setFocusable( false );
    JScrollPane comOMScroll = new JScrollPane( comOutgoingMsg );
    comOMScroll.setBorder( BorderFactory.createTitledBorder( "Outgoing messages" ) );

    // ## other com info
    comInfo = new List();
    comInfo.setFocusable( false );
    JScrollPane comInfoScroll = new JScrollPane( comInfo );
    comInfoScroll.setBorder( BorderFactory.createTitledBorder( "Communication Info" ) );

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


  @Override
  public void notifyOutgoingMessage ( Serializable msg )
  {
    comOutgoingMsg.add( msg.toString() + "(Sending)" );
  }

  @Override
  public void notifyJoin ( String groupID )
  {
    comInfo.add( "Joining " + groupID );
  }

  @Override
  public void notifyConnect ()
  {
  comInfo.add( "connected" );
  }
}

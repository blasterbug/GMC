package se.umu.cs.dist.ht15.dali_ens15bsf.debug.gui;

import se.umu.cs.dist.ht15.dali_ens15bsf.Gcom;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.ComMessage;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.debug.ComDebugObserver;
import se.umu.cs.dist.ht15.dali_ens15bsf.debug.GcomDebugObserver;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ens15bsf on 2015-11-04.
 */
public class GcomDebugGUI extends JFrame implements ComDebugObserver, GcomDebugObserver
{

  private DefaultListModel<String> comIncomimgModel;
  private DefaultListModel<String> comOutgoingModel;
  private DefaultListModel<String> comInfoModel;
  private TextArea delay;

  public GcomDebugGUI ( Gcom model )
  {

    super( "Gcom debug GUI" );
    /*try
    {
      GcomDebug debugModel = new GcomDebug( model );
    }
    catch ( RemoteException | NamingServiceUnavailableException e )
    {
      e.printStackTrace();
      System.exit( 1 );
    }*/

    // ######## COM MODULE DATA
    JPanel comStuff = new JPanel();
    GroupLayout comLayout = new GroupLayout( comStuff );
    comStuff.setLayout( comLayout );
    add( comStuff );
    // ## Incoming messages
    comIncomimgModel = new DefaultListModel();
    JList comIncomimgMsgList = new JList( comIncomimgModel );
    comIncomimgMsgList.setFocusable( false );
    JScrollPane comIMScroll = new JScrollPane( comIncomimgMsgList );
    comIMScroll.setBorder( BorderFactory.createTitledBorder( "Incoming messages" ) );

    // ## Outgoing messages
    comOutgoingModel = new DefaultListModel();
    JList comOutgoingMsgList = new JList( comOutgoingModel );
    comOutgoingMsgList.setFocusable( false );
    JScrollPane comOMScroll = new JScrollPane( comOutgoingMsgList );
    comOMScroll.setBorder( BorderFactory.createTitledBorder( "Outgoing messages" ) );

    // ## other com info
    comInfoModel = new DefaultListModel();
    JList comInfoList = new JList<String>( comInfoModel );
    comInfoList.setFocusable( false );
    JScrollPane comInfoScroll = new JScrollPane( comInfoList );
    comInfoScroll.setBorder( BorderFactory.createTitledBorder( "Communication Info" ) );

    comLayout.setAutoCreateContainerGaps( false );
    comLayout.setAutoCreateGaps( true );
    comLayout.setVerticalGroup(
            comLayout.createSequentialGroup()
                    .addComponent( comIMScroll )
                    .addComponent( comOMScroll )
                    .addComponent( comInfoScroll )
    );
    comLayout.setHorizontalGroup(
            comLayout.createSequentialGroup()
                    .addGroup( comLayout.createParallelGroup( GroupLayout.Alignment.CENTER )
                                    .addComponent( comIMScroll )
                                    .addComponent( comOMScroll )
                                    .addComponent( comInfoScroll )
                    )
    );

    // ######## ORDERING MODULE DATA
    //TODO
  }

  @Override
  public void notifyIncomingComMessage ( ComMessage msg )
  {
    comIncomimgModel.addElement( "(" + currentTime() + ") " + msg.toString() );
  }

  @Override
  public void notifyOutgoingComMessage ( ComMessage msg )
  {
    comOutgoingModel.addElement( "(" + currentTime() + ") " + msg.toString() );
  }


  @Override
  public void notifyOutgoingMessage ( Serializable msg )
  {
    comOutgoingModel.addElement( "(" + currentTime() + ") " + msg.toString() );
  }

  @Override
  public void notifyIncomingMessage ( Serializable msg )
  {
    comIncomimgModel.addElement( "(" + currentTime() + ") " + msg.toString() );
  }

  @Override
  public void notifyJoin ( String groupID )
  {
    comInfoModel.addElement( "(" + currentTime() + ") Joining " + groupID );
  }

  @Override
  public void notifyConnect ()
  {
    comInfoModel.addElement( "(" + currentTime() + ") connected" );
  }

  private String currentTime ()
  {
    DateFormat formattor = new SimpleDateFormat("HH:mm:ss");
    return formattor.format( new Date() );
  }
}

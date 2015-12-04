package se.umu.cs.dist.ht15.dali_ens15bsf.debug.gui;

import se.umu.cs.dist.ht15.dali_ens15bsf.GcomDebug;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.ComMessage;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.debug.ComMemberDebugObserver;
import se.umu.cs.dist.ht15.dali_ens15bsf.debug.GcomDebugObserver;

import javax.swing.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ens15bsf on 2015-11-04.
 */
public class GcomDebugGUI extends JFrame implements GcomDebugObserver, ComMemberDebugObserver
{

  private DefaultListModel<String> IncomingWaitingMessages;
  private DefaultListModel<String> IncomingDeliveredMessages;
  private DefaultListModel<String> OutgoingMessagesMessages;
  private DefaultListModel<String> comInfoModel;

  public GcomDebugGUI ( GcomDebug model )
  {

    super( "Gcom debug GUI" );


    // ######## COM MODULE DATA
    JPanel comStuff = new JPanel();
    GroupLayout comLayout = new GroupLayout( comStuff );
    comStuff.setLayout( comLayout );
    add( comStuff );
    // ## Incoming waiting queue
    // ## Incoming messages (send to the messages order)
    IncomingWaitingMessages = new DefaultListModel();
    JList DelayedMsgList = new JList( IncomingWaitingMessages );
    DelayedMsgList.setFocusable( false );
    JScrollPane delayedMsgSP = new JScrollPane( DelayedMsgList );
    delayedMsgSP.setBorder( BorderFactory.createTitledBorder( "Waiting messages" ) );
    // ## Incoming messages (send to the messages order)
    IncomingDeliveredMessages = new DefaultListModel();
    JList IncomimgMsgList = new JList( IncomingDeliveredMessages );
    IncomimgMsgList.setFocusable( false );
    JScrollPane incomingMsgSP = new JScrollPane( IncomimgMsgList );
    incomingMsgSP.setBorder( BorderFactory.createTitledBorder( "Delivered messages" ) );
    // ## Outgoing messages
    OutgoingMessagesMessages = new DefaultListModel();
    JList OutgoingMsgList = new JList( OutgoingMessagesMessages );
    OutgoingMsgList.setFocusable( false );
    JScrollPane outgoingMsgSP = new JScrollPane( OutgoingMsgList );
    outgoingMsgSP.setBorder( BorderFactory.createTitledBorder( "Outgoing messages" ) );

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
                    .addComponent( delayedMsgSP )
                    .addComponent( incomingMsgSP )
                    .addComponent( outgoingMsgSP )
                    .addComponent( comInfoScroll )
    );
    comLayout.setHorizontalGroup(
            comLayout.createSequentialGroup()
                    .addGroup( comLayout.createParallelGroup( GroupLayout.Alignment.CENTER )
                                    .addComponent( delayedMsgSP )
                                    .addComponent( incomingMsgSP )
                                    .addComponent( outgoingMsgSP )
                                    .addComponent( comInfoScroll )
                    )
    );



    // ######## ORDERING MODULE DATA
    //TODO
    // Maybe not so useful
  }

  @Override
  public void notifyOutgoingMessage ( Serializable msg )
  {
    OutgoingMessagesMessages.addElement( currentTime() + " : " + msg.toString() );
  }

  @Override
  public void notifyIncomingMessage ( Serializable msg )
  {
    IncomingDeliveredMessages.addElement( currentTime() + " : " + msg.toString() );
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

  @Override
  public void notifyQueued ( int i, ComMessage msg )
  {

  }
}

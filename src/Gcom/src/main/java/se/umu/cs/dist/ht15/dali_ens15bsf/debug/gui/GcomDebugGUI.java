package se.umu.cs.dist.ht15.dali_ens15bsf.debug.gui;

import se.umu.cs.dist.ht15.dali_ens15bsf.GcomDebug;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.debug.ComDebugObserver;
import se.umu.cs.dist.ht15.dali_ens15bsf.debug.GcomDebugObserver;
import se.umu.cs.dist.ht15.dali_ens15bsf.debug.TimeUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

/**
 * Created by ens15bsf on 2015-11-04.
 */
public class GcomDebugGUI extends JFrame implements GcomDebugObserver, ComDebugObserver
{

  private DefaultListModel<String> incomingWaitingMessages;
  private DefaultListModel<String> incomingDeliveredMessages;
  private DefaultListModel<String> outgoingMessagesMessages;
  private DefaultListModel<String> infoModel;

  public GcomDebugGUI ( final GcomDebug model )
  {

    super( "Gcom debug GUI" );


    // ######## COM MODULE DATA
    JPanel comStuff = new JPanel();
    GroupLayout comLayout = new GroupLayout( comStuff );
    comStuff.setLayout( comLayout );
    add( comStuff );
    // ## Incoming waiting queue
    incomingWaitingMessages = new DefaultListModel();
    final JList delayedMsgList = new JList( incomingWaitingMessages );
    delayedMsgList.setFocusable( false );
    delayedMsgList.setSelectionMode( ListSelectionModel.SINGLE_SELECTION  );
    JScrollPane delayedMsgSP = new JScrollPane( delayedMsgList );
    // panel for the buttons
    JButton wmRemove = new JButton( "remove" );
    JButton wmSend = new JButton( "deliver" );
    JPanel wmButtonsPanel = new JPanel();
    wmButtonsPanel.setLayout( new BoxLayout( wmButtonsPanel, BoxLayout.LINE_AXIS ) );
    wmButtonsPanel.add( Box.createHorizontalGlue() );
    wmButtonsPanel.add( wmRemove );
    wmButtonsPanel.add( Box.createRigidArea( new Dimension( 5, 0 ) ) );
    wmButtonsPanel.add( wmSend );
    wmButtonsPanel.add( Box.createRigidArea( new Dimension( 5, 0 ) ) );

    JPanel wmPanel = new JPanel();
    wmPanel.setBorder(  BorderFactory.createTitledBorder( "Waiting messages" ) );
    GroupLayout wmPanelLayout = new GroupLayout( wmPanel );
    wmPanel.setLayout( wmPanelLayout );
    wmPanelLayout.setAutoCreateContainerGaps( false );
    wmPanelLayout.setAutoCreateGaps( true );
    wmPanelLayout.setVerticalGroup(
            wmPanelLayout.createSequentialGroup()
                    .addComponent( delayedMsgSP )
                    .addComponent( wmButtonsPanel )
    );
    wmPanelLayout.setHorizontalGroup(
            wmPanelLayout.createSequentialGroup()
                    .addGroup( wmPanelLayout.createParallelGroup( GroupLayout.Alignment.CENTER )
                                    .addComponent( delayedMsgSP )
                                    .addComponent( wmButtonsPanel )
                    )
    );
    // ## Incoming messages (send to the messages order)
    incomingDeliveredMessages = new DefaultListModel();
    JList incomimgMsgList = new JList( incomingDeliveredMessages );
    incomimgMsgList.setFocusable( false );
    JScrollPane incomingMsgSP = new JScrollPane( incomimgMsgList );
    incomingMsgSP.setBorder( BorderFactory.createTitledBorder( "Delivered messages" ) );
    // ## Outgoing messages
    outgoingMessagesMessages = new DefaultListModel();
    JList outgoingMsgList = new JList( outgoingMessagesMessages );
    outgoingMsgList.setFocusable( false );
    JScrollPane outgoingMsgSP = new JScrollPane( outgoingMsgList );
    outgoingMsgSP.setBorder( BorderFactory.createTitledBorder( "Outgoing messages" ) );

    // ## other com info
    infoModel = new DefaultListModel();
    JList comInfoList = new JList<String>( infoModel );
    comInfoList.setFocusable( false );
    JScrollPane comInfoScroll = new JScrollPane( comInfoList );
    comInfoScroll.setBorder( BorderFactory.createTitledBorder( "Communication Info" ) );

    comLayout.setAutoCreateContainerGaps( false );
    comLayout.setAutoCreateGaps( true );
    comLayout.setVerticalGroup(
            comLayout.createSequentialGroup()
                    .addComponent( wmPanel )
                    .addComponent( incomingMsgSP )
                    .addComponent( outgoingMsgSP )
                    .addComponent( comInfoScroll )
    );
    comLayout.setHorizontalGroup(
            comLayout.createSequentialGroup()
                    .addGroup( comLayout.createParallelGroup( GroupLayout.Alignment.CENTER )
                                    .addComponent( wmPanel )
                                    .addComponent( incomingMsgSP )
                                    .addComponent( outgoingMsgSP )
                                    .addComponent( comInfoScroll )
                    )
    );

    wmRemove.addActionListener( new ActionListener()
    {
      public void actionPerformed( ActionEvent e )
      {
        if ( ! delayedMsgList.isSelectionEmpty() )
        {
          int selectedMsg = delayedMsgList.getSelectedIndex();
          //incomingWaitingMessages.remove( selectedMsg );
          model.dropWaitingMessage( selectedMsg );
        }
      }
    } );

    wmSend.addActionListener( new ActionListener()
    {
      @Override
      public void actionPerformed( ActionEvent e )
      {
        if ( ! delayedMsgList.isSelectionEmpty() )
        {
          int selectedMsg = delayedMsgList.getSelectedIndex();
          //incomingDeliveredMessages.addElement( incomingWaitingMessages.get( selectedMsg ) );
          model.deliverWaitingMessage( selectedMsg );
          //notifyIncomingMessage( incomingWaitingMessages.get( selectedMsg ) );
        }

      }
    } );

    setDefaultCloseOperation( DO_NOTHING_ON_CLOSE );
    // ######## ORDERING MODULE DATA
    //TODO
    // Maybe not so useful
  }

  @Override
  public void notifyOutgoingMessage ( Serializable msg )
  {
    outgoingMessagesMessages.addElement( TimeUtils.currentTime() + " : " + msg.toString() );
  }

  @Override
  public void notifyIncomingMessage ( Serializable msg )
  {
    incomingDeliveredMessages.addElement( TimeUtils.currentTime() + " : " + msg.toString() );
  }

  @Override
  public void notifyJoin ( String groupID )
  {
    infoModel.addElement( "(" + TimeUtils.currentTime() + ") Joining " + groupID );
  }

  @Override
  public void notifyConnect ()
  {
    infoModel.addElement( "(" + TimeUtils.currentTime() + ") connected" );
  }

  @Override
  public void notifyQueued ( int i, String msg )
  {
    incomingWaitingMessages.addElement( TimeUtils.currentTime() + " : " + msg.toString() );
  }

  @Override
  public void notifyDropped ( int messageIndex )
  {
    String msg = incomingWaitingMessages.remove( messageIndex );
    infoModel.addElement( TimeUtils.currentTime() + " : " + msg + "(dropped)" );
  }

  @Override
  public void notifyUnqueued( int messageIndex )
  {
    String msg = incomingWaitingMessages.remove( messageIndex );
    incomingDeliveredMessages.addElement( TimeUtils.currentTime() + " : " + msg );
  }

}

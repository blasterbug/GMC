package se.umu.cs.dist.ht15.dali_ens15bsf.debug.gui;

import se.umu.cs.dist.ht15.dali_ens15bsf.GcomDebug;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.ComMessage;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.ComObserver;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.RemoteMember;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.debug.ComDebugObserver;
import se.umu.cs.dist.ht15.dali_ens15bsf.debug.GcomDebugObserver;
import se.umu.cs.dist.ht15.dali_ens15bsf.debug.TimeUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * Created by ens15bsf on 2015-11-04.
 */
public class GcomDebugGUI extends JFrame implements GcomDebugObserver, ComDebugObserver, ComObserver
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
    delayedMsgSP.setBorder( BorderFactory.createTitledBorder( "Waiting messages" ) );
    // panel for the buttons
    JButton wmRemove = new JButton( "remove" );
    JButton wmSend = new JButton( "deliver" );
    JPanel wmPanel = new JPanel();
    wmPanel.setLayout( new BoxLayout( wmPanel,BoxLayout.LINE_AXIS ) );
    wmPanel.add( Box.createHorizontalGlue() );
    wmPanel.add( wmRemove );
    wmPanel.add( Box.createRigidArea( new Dimension( 5, 0 ) ) );
    wmPanel.add( wmSend );
    wmPanel.add( Box.createRigidArea( new Dimension( 5, 0 ) ) );
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
                    .addComponent( delayedMsgSP )
                    .addComponent( wmPanel )
                    .addComponent( incomingMsgSP )
                    .addComponent( outgoingMsgSP )
                    .addComponent( comInfoScroll )
    );
    comLayout.setHorizontalGroup(
            comLayout.createSequentialGroup()
                    .addGroup( comLayout.createParallelGroup( GroupLayout.Alignment.CENTER )
                                    .addComponent( delayedMsgSP )
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
          incomingWaitingMessages.remove( delayedMsgList.getSelectedIndex() );
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
          //notifyIncomingMessage( incomingWaitingMessages.get( delayedMsgList.getSelectedIndex() ) );
        }

      }
    } );


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
  public void notifyunQueued ( int messageIndex )
  {
    incomingWaitingMessages.remove( messageIndex );
  }

  /**
   * Notify Observers when a new incoming message arrive
   *
   * @param msg message to give to the Observer
   */
  @Override
  public void notifyObservers ( ComMessage msg )
  {
    incomingDeliveredMessages.addElement( TimeUtils.currentTime() + " : " + msg.toString() );
  }

  /**
   * Notify observer when new member want to join a group
   *
   * @param member  New member joining to the group
   * @param groupID Group name to join
   */
  @Override
  public void notifyNewMember ( RemoteMember member, String groupID )
  {
    try
    {
      infoModel.addElement( TimeUtils.currentTime() + " : " + member.getId() + " joined " + groupID );
    }
    catch ( RemoteException e )
    {
      infoModel.addElement( TimeUtils.currentTime() + " : A new member joined " + groupID );
    }
  }

  @Override
  public void notifyAddToView ( RemoteMember m, String id )
  {
    try
    {
      infoModel.addElement( TimeUtils.currentTime() + " : " + m.getId() + " added to " + id );
    }
    catch ( RemoteException e )
    {
      infoModel.addElement( TimeUtils.currentTime() + " : A new member added to " + id );
    }
  }

  @Override
  public void notifyNewLeader ( RemoteMember newLeader, String groupId )
  {
    try
    {
      infoModel.addElement( TimeUtils.currentTime() + " : " + newLeader.getId() + " is new leader of " + groupId );
    }
    catch ( RemoteException e )
    {
      infoModel.addElement( TimeUtils.currentTime() + " : There is new leader of " + groupId );
    }
  }

  @Override
  public void notifyRemoveFromView ( RemoteMember m, String id )
  {
    try
    {
      infoModel.addElement( TimeUtils.currentTime() + " : " + m.getId() + " was removed from " + id );
    }
    catch ( RemoteException e )
    {
      infoModel.addElement( TimeUtils.currentTime() + " : a member wa remove from " + id );
    }
  }
}

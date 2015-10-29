package se.umu.cs.ht15.dali_ens15bsf.view;


import se.umu.cs.ht15.dali_ens15bsf.model.GMessage;
import se.umu.cs.ht15.dali_ens15bsf.model.GModelObserver;
import se.umu.cs.ht15.dali_ens15bsf.model.GUser;
import se.umu.cs.ht15.dali_ens15bsf.model.Gchat;
import se.umu.cs.ht15.dali_ens15bsf.view.listeners.SendAction;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ens15bsf on 2015-10-21.
 */
public class ChatWindow extends JFrame implements GModelObserver
{

  private Gchat model;
  private JList<GUser> listUsers;
  private JPanel messageList;
  private JTextArea chatInput;


  public ChatWindow ( Gchat chat )
  {
    // create the windows
    super( "Gchat - " + chat.getUserName() );
    model = chat;
    model.addObserver( this );

    // Create a spilt panel for the list of users
    listUsers = new JList<GUser>();
    listUsers.setSize( new Dimension( Short.MAX_VALUE, Short.MAX_VALUE ) );
    listUsers.setLayout( new BoxLayout( listUsers, BoxLayout.PAGE_AXIS ) );
    JScrollPane scrollUSR = new JScrollPane( listUsers );

    for ( GUser usr : model.getUsers() )
      listUsers.add( usr );

    // panel for messages
    messageList = new JPanel();
    messageList.setAutoscrolls( true );
    messageList.setLayout( new BoxLayout( messageList, BoxLayout.PAGE_AXIS ) );
    JScrollPane scrollMSG = new JScrollPane( messageList );

    //Create a split pane with the two scroll panes in it.
    JSplitPane topPanel = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, scrollMSG, scrollUSR );
    topPanel.setAutoscrolls( true );
    topPanel.setOneTouchExpandable( true );
    topPanel.setDividerLocation( 600 );

    for ( GMessage gmsg : model.getMessages() )
      messageList.add( gmsg );

    // create the panel with the text input and the join button
    JPanel bottomPanel = new JPanel();
    // define a layout for the panel
    GroupLayout bottomLayout = new GroupLayout( bottomPanel );
    bottomPanel.setLayout( bottomLayout );
    bottomLayout.setAutoCreateGaps( true );
    bottomLayout.setAutoCreateContainerGaps( false );

    // define the components of the panel
    // a text area
    chatInput = new JTextArea( "Chat here" );
    JScrollPane chatInputWrapper = new JScrollPane( chatInput );
    chatInput.setRows( 2 );
    // and a button
    JButton sendBT = new JButton( "send" );
    sendBT.addActionListener( new SendAction( chat, chatInput ) );
    sendBT.setMinimumSize( new Dimension( 50, 30 ) );
    // add the components to the bottom panel
    bottomLayout.setHorizontalGroup(
            bottomLayout.createSequentialGroup()
                    .addComponent( chatInputWrapper )
                    .addComponent( sendBT )
                    .addGap( 5 )
    );
    bottomLayout.setVerticalGroup(
            bottomLayout.createSequentialGroup()
                    .addGroup( bottomLayout.createParallelGroup( GroupLayout.Alignment.CENTER )
                                    .addComponent( chatInputWrapper, 50, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE )
                                    .addComponent( sendBT )
                                    .addGap( 5 )
                    )
    );

    JPanel mainPane = new JPanel();
    super.setContentPane( mainPane );

    GroupLayout mainLayout = new GroupLayout( mainPane );

    mainPane.setLayout( mainLayout );
    mainLayout.setAutoCreateContainerGaps( false );
    mainLayout.setAutoCreateGaps( false );
    mainLayout.setVerticalGroup(
            mainLayout.createSequentialGroup()
                    .addComponent( topPanel )
                    .addComponent( bottomPanel )
    );
    mainLayout.setHorizontalGroup(
            mainLayout.createSequentialGroup()
                    .addGroup( mainLayout.createParallelGroup( GroupLayout.Alignment.CENTER )
                                    .addComponent( topPanel )
                                    .addComponent( bottomPanel )
                    )
    );
    // define the behavior when clicking on the close button
    super.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    super.setSize( new Dimension( 800, 500 ) );
    super.setLocationRelativeTo( null );
    //super.pack();
  }


  @Override
  public void newMessage ()
  {
    messageList.removeAll();
    for ( GMessage msg : model.getMessages() )
      messageList.add( msg );

  }

  public void newUser ()
  {
    listUsers.removeAll();
    for ( GUser usr : model.getUsers() )
      listUsers.add( usr );
  }
}

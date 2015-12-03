package se.umu.cs.ht15.dali_ens15bsf.view;


import se.umu.cs.ht15.dali_ens15bsf.model.*;
import se.umu.cs.ht15.dali_ens15bsf.model.msg.GMessage;
import se.umu.cs.ht15.dali_ens15bsf.view.listeners.EnterToSend;
import se.umu.cs.ht15.dali_ens15bsf.view.listeners.SendAction;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ens15bsf on 2015-10-21.
 */
public class ChatWindow extends JFrame implements GModelObserver, ConnectionObserver
{

  private Gchat model;
  private GchatListModel<GUser> listUsersModel;
  private JPanel messageList;
  private BoxLayout listLayout;
  private JScrollPane scrollMSG;
  private JTextArea chatInput;


  public ChatWindow ( Gchat chat )
  {
    // create the windows
    super( "Gchat - [...]" );
    model = chat;
    model.addObserver( this );
    model.addConnectionObserver( this );

    // Create a spilt panel for the list of users
    listUsersModel = new GchatListModel<>();
    JList usersList = new JList( listUsersModel );
    usersList.setCellRenderer( new GUserListCellRender() );
    usersList.setLayout( new BoxLayout( usersList, BoxLayout.PAGE_AXIS ) );
    //listUsers.setFixedCellHeight( 60 );
    usersList.setLayoutOrientation( JList.VERTICAL );

    // panel for messages
    messageList = new JPanel();
    messageList.setAutoscrolls( false );
    listLayout = new BoxLayout( messageList, BoxLayout.PAGE_AXIS );
    messageList.setLayout( listLayout );
    JViewport viewportMessages = new JViewport();
    viewportMessages.setLayout( new BoxLayout( viewportMessages, BoxLayout.PAGE_AXIS ) );
    viewportMessages.setView( messageList );
    scrollMSG = new JScrollPane( viewportMessages );

    //Create a split pane with the two scroll panes in it.
    JSplitPane topPanel = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, scrollMSG,new ScrollPane().add( usersList ) );
    //topPanel.updateUI();
    topPanel.setAutoscrolls( true );
    topPanel.setOneTouchExpandable( true );
    topPanel.setDividerLocation( 600 );

    scrollMSG.setAutoscrolls( true );

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
    chatInput.setLineWrap( true );
    chatInput.setWrapStyleWord( true );
    chatInput.setLayout( new BorderLayout() );
    chatInput.addKeyListener( new EnterToSend( chat, chatInput ) );
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
    // super.pack();
    // define the behavior when clicking on the close button
    super.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    super.setSize( new Dimension( 800, 500 ) );
    super.setLocationRelativeTo( null );
  }


  public void newMessage ()
  {
    messageList.removeAll();
    for ( GMessage msg : model.getMessages() )
      messageList.add( new GMessageDisplay( model.getUser( msg.getAuthor() ), msg ) );
    messageList.add( Box.createVerticalGlue() );
    messageList.updateUI();
    chatInput.requestFocus();
    scrollMSG.getVerticalScrollBar().setValue( scrollMSG.getVerticalScrollBar().getMaximum() );
  }

  public void newUser ()
  {
    listUsersModel.removeAll();
    for ( GUser usr : model.getUsers() )
      listUsersModel.addElement( usr );
  }

  /**
   * Get notified when a user is connected
   *
   * @param guid Group ID
   */
  @Override
  public void connected ( String guid )
  {
    setTitle( "GChat - " + model.getUserName() + " @ " + guid );
    newUser();
    newMessage();
    setVisible( true );
  }

  /**
   * Get notified when the current is trying to join a group
   *
   * @param uid username
   */
  @Override
  public void connecting ( String uid )
  {
    // DO NOTHING
  }

  /**
   * Get notified when an user get disconnected
   *
   * @param uid disconnected user ID
   */
  @Override
  public void disconnected ( String uid )
  {
    setVisible( false );
  }
}

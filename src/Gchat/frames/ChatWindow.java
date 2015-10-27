package Gchat.frames;

import Gchat.Listeners.SendAction;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by ens15bsf on 2015-10-21.
 */
public class ChatWindow extends JFrame implements Observer
{

  private String[] messages;
  private String[] users;
  private String userName;


  public ChatWindow ( String userName )
  {
    // create the windows
    super( "Gchat - " + userName );
    this.userName = userName;
    this.users = new String[] { "user1", "user2", "user3", "user4" };
    this.messages = new String[] {
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            "Donec a diam lectus. Sed sit amet ipsum mauris. Maecenas congue\n ligula ac quam viverra nec consectetur ante hendrerit.",
            "Donec et mollis dolor.",
            "Praesent et diam eget libero egestas mattis sit amet vitae augue.",
            "Nam tincidunt congue enim, ut porta lorem lacinia consectetur.",
            "Donec ut libero sed arcu vehicula ultricies a non tortor."
    };

    // Create a spilt panel for the list of users
    JScrollPane usersList = new JScrollPane();
    JList<String> users = new JList<String>( this.users );
    usersList.getViewport().setView( users );
    usersList.setPreferredSize( new Dimension( 200, 400 ) );

    // panel for messages
    JScrollPane messagesPanel = new JScrollPane();
    messagesPanel.setPreferredSize( new Dimension( 600, 400 ) );
    messagesPanel.setSize( 600, 400 );
    messagesPanel.getViewport().setView( new JList<String>( messages ) );

    //Create a split pane with the two scroll panes in it.
    JSplitPane topPanel = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, messagesPanel, usersList );
    topPanel.setOneTouchExpandable( true );
    topPanel.setDividerLocation( 600 );


    // create the panel with the text input and the join button
    JPanel bottomPanel = new JPanel();
    // define a layout for the panel
    GroupLayout bottomLayout = new GroupLayout( bottomPanel );
    bottomPanel.setLayout( bottomLayout );
    bottomLayout.setAutoCreateGaps( true );
    bottomLayout.setAutoCreateContainerGaps( false );

    // define the components of the panel
    // a text area
    JTextArea chatInput = new JTextArea( "Chat here" );
    JScrollPane chatInputWrapper = new JScrollPane( chatInput );
    chatInput.setRows( 2 );
    // and a button
    JButton sendBT = new JButton( "send" );
    sendBT.addActionListener( new SendAction() );
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
  }


  @Override
  public void update( Observable observable, Object o )
  {
    //TODO
    super.setName( "Gchat - " + userName + " (TODO)" );
  }
}

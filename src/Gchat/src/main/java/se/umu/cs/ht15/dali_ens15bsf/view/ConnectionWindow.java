package se.umu.cs.ht15.dali_ens15bsf.view;


import se.umu.cs.ht15.dali_ens15bsf.model.Gchat;
import se.umu.cs.ht15.dali_ens15bsf.view.listeners.ConnectAction;
import se.umu.cs.ht15.dali_ens15bsf.view.listeners.FocusObserver;
import se.umu.cs.ht15.dali_ens15bsf.view.listeners.SwitchSelectedGroup;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ens15bsf on 2015-10-13.
 * Code for the app demonstration of Gcom Module
 */
public class ConnectionWindow extends JFrame implements ConnectionObserver, FocusObserver

{
  public final static String CREATE_GROUP = "Create a new group?";

  private JTextField userName;
  private JTextField newGroupName;
  private DefaultListModel listGroupsModel;
  private JList groups;
  private boolean groupsChoosen;

  public ConnectionWindow ( Gchat model )
  {

    // create the windows
    super( "Gchat - Join a group" );
    groupsChoosen = false;
    model.addConnectionObserver( this );
    listGroupsModel = new DefaultListModel();
    for ( String group : model.getAvailableGroups() )
      listGroupsModel.addElement( group );

    // Create a panel for the list of available groups
    JScrollPane listAvailableGroups = new JScrollPane();
    if ( listGroupsModel.isEmpty() )
      listAvailableGroups.setEnabled( false );
    groups = new JList( listGroupsModel );
    groups.addFocusListener( new SwitchSelectedGroup( this, true ) );
    groups.setSelectedIndex( 0 );
    listAvailableGroups.getViewport().setView( groups );
    if ( listGroupsModel.size() == 0 )
    {
      groups.setEnabled( false );
      groups.setFocusable( false );
    }
    listAvailableGroups.setPreferredSize( new Dimension( 220, 100 ) );
    groups.setAutoscrolls( true );

    // create the panel with the text input and the join button
    JPanel newGroupPanel = new JPanel();
    // define a layout for the panel
    GroupLayout newGroupLayout = new GroupLayout( newGroupPanel );
    newGroupPanel.setLayout( newGroupLayout );
    newGroupLayout.setAutoCreateGaps( true );
    newGroupLayout.setAutoCreateContainerGaps( false );
    // define the components of the panel to create a group
    // a text area
    newGroupName = new JTextField( model.getGroupName(), 30 );
    newGroupName.addFocusListener( new SwitchSelectedGroup( this, false ) );
    // and a button
    JLabel newGroupLabel = new JLabel( "Join : " );
    // add the components to the bottom panel
    newGroupLayout.setHorizontalGroup(
            newGroupLayout.createSequentialGroup()
                    .addComponent( newGroupLabel )
                    .addComponent( newGroupName )
    );
    newGroupLayout.setVerticalGroup(
            newGroupLayout.createSequentialGroup()
                    .addGroup( newGroupLayout.createParallelGroup( GroupLayout.Alignment.BASELINE )
                            .addComponent( newGroupLabel )
                            .addComponent( newGroupName ) )
    );

    // create the panel with the text input and the join button
    JPanel joinPanel = new JPanel();
    // define a layout for the panel
    GroupLayout JoinPanelLayout = new GroupLayout( joinPanel );
    joinPanel.setLayout( JoinPanelLayout );
    JoinPanelLayout.setAutoCreateGaps( true );
    JoinPanelLayout.setAutoCreateContainerGaps( false );
    // define the components of the panel for join a group
    // a text area
    userName = new JTextField( model.getUserName(), 30 );
    userName.addActionListener( new ConnectAction( this, model ) );
    // and a button
    JButton joinBT = new JButton( "join" );
    joinBT.addActionListener( new ConnectAction( this, model ) );
    // add the components to the bottom panel
    JoinPanelLayout.setHorizontalGroup(
            JoinPanelLayout.createSequentialGroup()
                    .addComponent( userName )
                    .addComponent( joinBT )
    );
    JoinPanelLayout.setVerticalGroup(
            JoinPanelLayout.createSequentialGroup()
                    .addGroup( JoinPanelLayout.createParallelGroup( GroupLayout.Alignment.BASELINE )
                            .addComponent( userName )
                            .addComponent( joinBT ) )
    );

    // create the panel with the text input and the join button
    JPanel bottomPanel = new JPanel();
    // define a layout for the panel
    GroupLayout bottomLayout = new GroupLayout( bottomPanel );
    bottomPanel.setLayout( bottomLayout );
    bottomLayout.setAutoCreateGaps( true );
    bottomLayout.setAutoCreateContainerGaps( false );
    // add the components to the bottom panel
    bottomLayout.setVerticalGroup(
            bottomLayout.createSequentialGroup()
                    .addComponent( newGroupPanel )
                    .addComponent( joinPanel )
    );
    bottomLayout.setHorizontalGroup(
            bottomLayout.createSequentialGroup()
                    .addGroup( bottomLayout.createParallelGroup( GroupLayout.Alignment.CENTER )
                            .addComponent( newGroupPanel )
                            .addComponent( joinPanel ) )
    );


    // define the behavior when clicking on the close button
    super.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    JPanel mainPane = new JPanel();
    super.setContentPane( mainPane );

    GroupLayout mainLayout = new GroupLayout( mainPane );
    mainPane.setLayout( mainLayout );
    mainLayout.setAutoCreateContainerGaps( true );
    mainLayout.setAutoCreateGaps( true );

    mainLayout.setVerticalGroup(
            mainLayout.createSequentialGroup()
                    .addComponent( listAvailableGroups )
                    .addComponent( bottomPanel )
    );
    mainLayout.setHorizontalGroup(
            mainLayout.createSequentialGroup()
                    .addGroup( mainLayout.createParallelGroup( GroupLayout.Alignment.CENTER )
                            .addComponent( listAvailableGroups )
                            .addComponent( bottomPanel ) )
    );


    // dqrken window in connection
    getRootPane().setGlassPane( new JComponent()
    {
      public void paintComponent ( Graphics g )
      {
        g.setColor( new Color( 0, 0, 0, 100 ) );
        g.fillRect( 0, 0, getWidth(), getHeight() );
        super.paintComponent( g );
      }
    } );
    super.setSize( new Dimension( 260, 250 ) );
    super.setLocationRelativeTo( null );
    super.setResizable( false );
    //super.setVisible( true );
  }


  public String getGroupConnection ()
  {
    // Buggy
    if ( groupsChoosen )
      return (String)groups.getSelectedValue();
    else
      return newGroupName.getText();
  }

  public String getUserName ()
  {
    return userName.getText();
  }


  public void addGroup ( String newGroup )
  {
    listGroupsModel.addElement( newGroup );
    groups.setEnabled( true );
    groups.setFocusable( true );
  }

  /**
   * Get notified when a user is connected
   *
   * @param uid New user ID
   */
  @Override
  public void connected ( String uid )
  {
    setVisible( false );
  }

  public void connecting ( String uid )
  {
    getRootPane().getGlassPane().setVisible( true );
    setEnabled( false );
  }

  /**
   * Get notified when an user get disconnected
   *
   * @param uid disconnected user ID
   */
  @Override
  public void disconnected ( String uid )
  {
    getRootPane().getGlassPane().setVisible( false );
    setEnabled( true );
    setVisible( true );
  }

  @Override
  public void getFocus ( boolean state )
  {
    groupsChoosen = state;
  }
}

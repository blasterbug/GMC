package se.umu.cs.ht15.dali_ens15bsf.view;


import se.umu.cs.ht15.dali_ens15bsf.view.listeners.AddGroupAction;
import se.umu.cs.ht15.dali_ens15bsf.view.listeners.ConnectAction;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Created by ens15bsf on 2015-10-13.
 * Code for the app demonstration of Gcom Module
 */
public class ConnectionWindow extends JFrame
{
  public final static String CREATE_GROUP = "Create a new group?";
  private static final String[] NAMES = {
          "King AdasKing Adas",
          "Padmé Amidala",
          "Darth Andeddu",
          "Jar Jar Binks",
          "Chewbacca",
          "Jango Fett",
          "Boba Fett",
          "Greedo",
          "General Grievous",
          "Jabba the Hutt",
          "Qui-Gon Jinn",
          "Obi-Wan Kenobi",
          "Galen Marek",
          "Darth Maul",
          "Princess Leia Organa",
          "Emperor Sheev Palpatine",
          "R2-D2",
          "Darth Sidious",
          "Anakin Skywalker",
          "Luke Skywalker",
          "Anakin Solo",
          "Han Solo",
          "Jaina Solo",
          "Ahsoka Tano",
          "Grand Moff Wilhuff Tarkin",
          "Darth Tyranus",
          "Darth Vader",
          "Watto",
          "Mace Windu",
          "Yoda"
  };
  private static final Random dice = new Random();

  private JTextField userName;
  private JTextField newGroupName;
  private String[] listGroups;
  private JList<String> groups;

  public ConnectionWindow ()
  {

    // create the windows
    super( "Gchat - Join a group" );
    listGroups = new String[] { "group1", "group2", "group3" };

    // Create a panel for the list of available groups
    JScrollPane list = new JScrollPane();
    groups = new JList<String>( listGroups );
    groups.setSelectedIndex( 0 );
    list.getViewport().setView( groups );
    list.setPreferredSize( new Dimension( 220, 100 ) );

    // create the panel with the text input and the join button
    JPanel newGroupPanel = new JPanel();
    // define a layout for the panel
    GroupLayout newGroupLayout = new GroupLayout( newGroupPanel );
    newGroupPanel.setLayout( newGroupLayout );
    newGroupLayout.setAutoCreateGaps( true );
    newGroupLayout.setAutoCreateContainerGaps( false );
    // define the components of the panel to create a group
    // a text area
    newGroupName = new JTextField( CREATE_GROUP, 30 );
    // and a button
    JButton addBT = new JButton( "add" );
    addBT.addActionListener( new AddGroupAction( this ) );
    // add the components to the bottom panel
    newGroupLayout.setHorizontalGroup(
            newGroupLayout.createSequentialGroup()
                    .addComponent( newGroupName )
                    .addComponent( addBT )
    );
    newGroupLayout.setVerticalGroup(
            newGroupLayout.createSequentialGroup()
                    .addGroup( newGroupLayout.createParallelGroup( GroupLayout.Alignment.BASELINE )
                            .addComponent( newGroupName )
                            .addComponent( addBT ) )
    );

    // create the panel with the text input and the join button
    JPanel JoinPanel = new JPanel();
    // define a layout for the panel
    GroupLayout JoinPanelLayout = new GroupLayout( JoinPanel );
    JoinPanel.setLayout( JoinPanelLayout );
    JoinPanelLayout.setAutoCreateGaps( true );
    JoinPanelLayout.setAutoCreateContainerGaps( false );
    // define the components of the panel for join a group
    // a text area
    userName = new JTextField( getRandomUserName(), 30 );
    userName.addActionListener( new ConnectAction( this ) );
    // and a button
    JButton joinBT = new JButton( "join" );
    joinBT.addActionListener( new ConnectAction( this ) );
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
                    .addComponent( JoinPanel )
    );
    bottomLayout.setHorizontalGroup(
            bottomLayout.createSequentialGroup()
                    .addGroup( bottomLayout.createParallelGroup( GroupLayout.Alignment.CENTER )
                            .addComponent( newGroupPanel )
                            .addComponent( JoinPanel ) )
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
                    .addComponent( list )
                    .addComponent( bottomPanel )
    );
    mainLayout.setHorizontalGroup(
            mainLayout.createSequentialGroup()
                    .addGroup( mainLayout.createParallelGroup( GroupLayout.Alignment.CENTER )
                            .addComponent( list )
                            .addComponent( bottomPanel ) )
    );


    super.setSize( new Dimension( 260, 250 ) );
    super.setLocationRelativeTo( null );
    super.setResizable( false );
    super.setVisible( true );
  }

  public String getGroupConnection ()
  {
    return groups.getSelectedValue();
  }

  public String getUserName ()
  {
    return userName.getText();
  }

  public String newGroupName ()
  {
    return newGroupName.getText();
  }

  public static String getRandomUserName ()
  {
    return NAMES[dice.nextInt( NAMES.length )];
  }

  public void addGroup ( String newGroup )
  {
    String[] newListGroups = new String[listGroups.length + 1];
    int i = 0;
    while ( i < listGroups.length )
    {
      newListGroups[i] = listGroups[i];
      i++;
    }
    newListGroups[i] = newGroup;
    listGroups = newListGroups;
    groups.setListData( listGroups );
    groups.setSelectedIndex( i );
  }
}

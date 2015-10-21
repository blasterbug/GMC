package Gchat.src.frames;

import Gchat.src.Listeners.ConnectAction;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ens15bsf on 2015-10-13.
 * Code for the app demonstration of Gcom Module
 */
public class ConnectionWindow
{
  private final static String CREATE_GROUP = "Create a new group?";
  private final static String NEW_GROUP = "new_group";
  private JFrame window;
  private JTextField groupInput;
  private JList<String> groups;

  public ConnectionWindow ()
  {


    // Create a panel for the list of available groups
    JScrollPane list = new JScrollPane();
    groups = new JList<String>( new String[] { "group1", "group2", "group3", CREATE_GROUP } );
    groups.setSelectedIndex( 0 );
    list.getViewport().setView( groups );
    list.setPreferredSize( new Dimension( 220, 100 ) );

    // create the panel with the text input and the join button
    JPanel bottomPanel = new JPanel();
    // define a layout for the panel
    GroupLayout bottomPLayout = new GroupLayout( bottomPanel );
    bottomPanel.setLayout( bottomPLayout );
    bottomPLayout.setAutoCreateGaps( true );
    bottomPLayout.setAutoCreateContainerGaps( false );

    // define the components of the panel
    // a text area
    groupInput = new JTextField( NEW_GROUP , 30 );
    groupInput.addActionListener( new ConnectAction( this ) );
    // and a button
    JButton joinBT = new JButton( "join" );
    joinBT.addActionListener( new ConnectAction( this ) );
    // add the components to the bottom panel
    bottomPLayout.setHorizontalGroup(
            bottomPLayout.createSequentialGroup()
                    .addComponent( groupInput )
                    .addComponent( joinBT )
    );
    bottomPLayout.setVerticalGroup(
            bottomPLayout.createSequentialGroup()
                    .addGroup( bottomPLayout.createParallelGroup( GroupLayout.Alignment.BASELINE )
                            .addComponent( groupInput )
                            .addComponent( joinBT ) )
    );


    // create the windows
    window = new JFrame( "Gchat - Join a group" );
    // define the behavior when clicking on the close button
    window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    JPanel mainPane = new JPanel();
    window.setContentPane( mainPane );

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


    window.setSize( new Dimension( 230, 200 ) );
    window.setResizable( false );
    window.setVisible( true );
  }

  public String getGroupConnection ()
  {
    if( groups.getSelectedValue() == null || groups.getSelectedValue().equals( CREATE_GROUP ))
        return groupInput.getText();
    else
      return groups.getSelectedValue();
  }

}

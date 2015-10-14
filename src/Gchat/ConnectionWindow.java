package Gchat;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ens15bsf on 2015-10-13.
 * Code for the app demonstration of Gcom Module
 */
public class ConnectionWindow
{
  private JFrame window;

  public ConnectionWindow ()
  {

    // Create a panel for the list of available groups
    JScrollPane list = new JScrollPane();
    JList<String> groups = new JList<String>( new String[] { "group1", "group2", "group3", "group#" } );
    list.getViewport().setView(groups);

    // create the panel with the tex input and the join button
    JPanel bottomPanel = new JPanel();
    // define a layout for the panel
    GroupLayout bottomPLayout = new GroupLayout( bottomPanel );
    bottomPanel.setLayout( bottomPLayout );

    // define the components of the panel
    // a text area
    JTextField groupInput = new JTextField( "Create a new group?", 30 );
    bottomPanel.add( groupInput );
    // and a button
    JButton joinBT = new JButton( "join" );
    bottomPanel.add( joinBT );

    // create the windows
    window = new JFrame( "Gchat - Join a group" );
    // define the behavior when clicking on the close button
    window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );


    //bottomPanel.setLayout(  );
    window.getContentPane().add( new JScrollPane( groups ), BorderLayout.CENTER );


    window.pack();
    window.setVisible( true );
  }

}

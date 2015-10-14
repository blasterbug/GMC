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
    window = new JFrame( "Connect to the a chat" );
    window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

    //window.getContentPane().add( new, BorderLayout.CENTER );

    window.pack();
    window.setVisible( true );
  }
}

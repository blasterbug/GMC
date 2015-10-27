package Gchat;

import Gchat.frames.ChatWindow;
import Gchat.frames.ConnectionWindow;

/**
 * Created by ens15bsf on 2015-10-13.
 */
public class AppChat
{

  public static void main ( String[] args )
  {
    // create connection object
    // get a list of the existing groups
    // offer to the user to join a group or to create a new one
    new ConnectionWindow();
    // connect to the group
    // open the chat window
    new ChatWindow( "Yoda" );
  }
}

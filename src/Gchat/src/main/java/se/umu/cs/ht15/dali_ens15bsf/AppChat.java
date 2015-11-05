package se.umu.cs.ht15.dali_ens15bsf;

import se.umu.cs.ht15.dali_ens15bsf.model.Gchat;
import se.umu.cs.ht15.dali_ens15bsf.model.UnableToJoinException;
import se.umu.cs.ht15.dali_ens15bsf.view.ChatWindow;
import se.umu.cs.ht15.dali_ens15bsf.view.ConnectionWindow;

import java.util.LinkedList;

/**
 * Created by ens15bsf on 2015-10-13.
 */
public class AppChat
{

  public static void main ( String[] args ) throws UnableToJoinException
  {
    // create connection object
    Gchat chatModel = new Gchat( ConnectionWindow.getRandomUserName() );
    // offer to the user to join a group or to create a new one
    // connect to the group

    // open the chat window




  }
}

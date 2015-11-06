package se.umu.cs.ht15.dali_ens15bsf;

import se.umu.cs.ht15.dali_ens15bsf.model.Gchat;
import se.umu.cs.ht15.dali_ens15bsf.model.UnableToJoinException;
import se.umu.cs.ht15.dali_ens15bsf.model.msg.GJoinMessage;
import se.umu.cs.ht15.dali_ens15bsf.view.ChatWindow;
import se.umu.cs.ht15.dali_ens15bsf.view.ConnectionWindow;

/**
 * Created by ens15bsf on 2015-10-13.
 */
public class AppChat
{

  public static void main ( String[] args ) throws UnableToJoinException
  {

    Gchat chatModel = new Gchat( RandomNames.getRandomUserName(), RandomNames.getRandomGroupName());
    ConnectionWindow connectionWindow = new ConnectionWindow( chatModel );
    ChatWindow chatView = new ChatWindow( chatModel );
    connectionWindow.setVisible( true );
    //System.exit( 0 );
  }
}

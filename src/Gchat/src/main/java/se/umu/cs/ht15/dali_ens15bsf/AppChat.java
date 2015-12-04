package se.umu.cs.ht15.dali_ens15bsf;

import se.umu.cs.ht15.dali_ens15bsf.model.Gchat;
import se.umu.cs.ht15.dali_ens15bsf.model.UnableToJoinException;
import se.umu.cs.ht15.dali_ens15bsf.view.ChatWindow;
import se.umu.cs.ht15.dali_ens15bsf.view.ConnectionWindow;

/**
 * Created by ens15bsf on 2015-10-13.
 */
public class AppChat
{

  private static final String HELP_MSG =
          "Run the Gchat\n debug : run apps with debug features\n";

  public static void main ( String[] args ) throws UnableToJoinException
  {
    boolean debugApp = false;
    if ( 0 < args.length )
    {
      switch ( args[0] )
      {
        case "help" :
          System.out.println(HELP_MSG);
          System.exit( 0 );
          break;
        case "debug" :
          System.out.println( "Debug mode" );
          debugApp = true;
          break;
        default:
          debugApp = false;
      }
    }
    Gchat chatModel = new Gchat( RandomNames.getRandomUserName(), RandomNames.getRandomGroupName(), debugApp );
    ConnectionWindow connectionWindow = new ConnectionWindow( chatModel );
    ChatWindow chatView = new ChatWindow( chatModel );
    connectionWindow.setVisible( true );
    //System.exit( 0 );
  }
}

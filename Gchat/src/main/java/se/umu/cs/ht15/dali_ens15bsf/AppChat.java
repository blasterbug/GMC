package se.umu.cs.ht15.dali_ens15bsf;

import se.umu.cs.dist.ht15.dali_ens15bsf.com.BasicReliableMulticast;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.TreeBaseMulticast;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.UnreachableRemoteObjectException;
import se.umu.cs.dist.ht15.dali_ens15bsf.groupmanagement.Member;
import se.umu.cs.dist.ht15.dali_ens15bsf.groupmanagement.MemberImpl;
import se.umu.cs.dist.ht15.dali_ens15bsf.ordering.CausalOrderer;
import se.umu.cs.ht15.dali_ens15bsf.model.Gchat;
import se.umu.cs.ht15.dali_ens15bsf.model.UnableToJoinException;
import se.umu.cs.ht15.dali_ens15bsf.view.ChatWindow;
import se.umu.cs.ht15.dali_ens15bsf.view.ConnectionWindow;

import java.rmi.RemoteException;
import java.util.LinkedList;

/**
 * Created by ens15bsf on 2015-10-13.
 */
public class AppChat
{

  public static void main ( String[] args ) throws UnableToJoinException
  {
    // create connection object
    // get a list of the existing groups
    // offer to the user to join a group or to create a new one
    new ConnectionWindow();
    // connect to the group

    LinkedList<String> users = new LinkedList<String>();
    // open the chat window
    Gchat chatModel = new Gchat( ConnectionWindow.getRandomUserName() );
    for ( int i = 0; i < 200; i++ )
    {
      String newUser = ConnectionWindow.getRandomUserName();
      if ( !( users.contains( newUser ) ) )
      {
        users.add( newUser );
        chatModel.addUser( newUser );
      }
    }

    ChatWindow chatView = new ChatWindow( chatModel );
    chatView.setVisible( true );
    chatModel.sendMessage( "Lorem ipsum dolor sit amet, consectetur adipiscing elit." );
    chatModel.sendMessage( "Lorem ipsum dolor sit amet, consectetur adipiscing elit." );



  }
}

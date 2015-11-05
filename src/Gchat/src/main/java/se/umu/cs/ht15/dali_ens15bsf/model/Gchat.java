package se.umu.cs.ht15.dali_ens15bsf.model;

import se.umu.cs.dist.ht15.dali_ens15bsf.*;
import se.umu.cs.dist.ht15.dali_ens15bsf.nameserver.NamingServiceUnavailableException;
import se.umu.cs.ht15.dali_ens15bsf.view.ChatWindow;
import se.umu.cs.ht15.dali_ens15bsf.view.ConnectionWindow;

import java.rmi.RemoteException;
import java.util.*;

/**
 * Created by ens15bsf on 2015-10-28.
 * model class for the Gchat
 */
public class Gchat implements Observer, GcomObserver
{
  private HashMap<String, GUser> users;
  private LinkedList<GMessageDisplay> messages;
  private GUser user;
  private LinkedList<GModelObserver> observers;
  private Gcom gcomMb;

  public Gchat ( String userName )
  {
    users = new HashMap<String, GUser>();
    messages = new LinkedList<GMessageDisplay>();
    user = new GUser( userName );
    users.put( userName, user );

    observers = new LinkedList<GModelObserver>();

    try
    {
      gcomMb = GcomFactory.createGcom( OrderingStrategyEnum.FIFO, MulticastStrategyEnum.RELIABLE_MULTICAST );
      gcomMb.addObserver( this );
      gcomMb.connect();
      ConnectionWindow cntView = new ConnectionWindow( this );
      cntView.setVisible( true );
    }
    catch ( RemoteException | NamingServiceUnavailableException e )
    {
      e.printStackTrace();
    }

  }

  public void connect () throws UnableToJoinException
  {
    gcomMb.connect();
    ChatWindow chatView = new ChatWindow( this );
    chatView.setVisible( true );
  }

  public void createGroup ( String groupName )
  {
    gcomMb.join( groupName );
    //window.addGroup( window.newGroupName() );
  }

  public Collection<GUser> getUsers ()
  {
    return users.values();
  }

  public LinkedList<GMessageDisplay> getMessages ()
  {
    return messages;
  }

  public GUser getUser ()
  {
    return user;
  }

  public String getUserName ()
  {
    return user.getName();
  }

  public void addUser ( String newUser )
  {
    users.put( newUser, new GUser( newUser ) );

    for ( GModelObserver ob : observers )
      ob.newUser();
  }

  public void sendMessage ( String content )
  {
    messages.addLast( new GMessageDisplay( user, content ) );
    for ( GModelObserver ob : observers )
      ob.newMessage();
  }

  public void addObserver ( GModelObserver obs )
  {
    observers.add( obs );
  }

  @Override
  public void update ( Observable observable, Object o )
  {
    System.out.println( o.toString() );
  }

  public String[] getAvailableGroups ()
  {
    return gcomMb.getGroups();
  }

  @Override
  public void newMessage ( Message message )
  {
    System.out.println( "TODO" );
    System.out.println( message.toString() );
  }

  public void join( String userName, String group )
  {
    gcomMb.join( group );
  }
}

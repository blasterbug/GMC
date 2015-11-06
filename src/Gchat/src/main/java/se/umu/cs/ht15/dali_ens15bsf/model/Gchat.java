package se.umu.cs.ht15.dali_ens15bsf.model;

import se.umu.cs.dist.ht15.dali_ens15bsf.*;
import se.umu.cs.dist.ht15.dali_ens15bsf.nameserver.NamingServiceUnavailableException;
import se.umu.cs.ht15.dali_ens15bsf.model.msg.GJoinMessage;
import se.umu.cs.ht15.dali_ens15bsf.model.msg.GMessage;
import se.umu.cs.ht15.dali_ens15bsf.model.msg.GTextMessage;
import se.umu.cs.ht15.dali_ens15bsf.view.ConnectionObserver;

import java.rmi.RemoteException;
import java.util.*;

/**
 * Created by ens15bsf on 2015-10-28.
 * model class for the Gchat
 */
public class Gchat implements Observer, GcomObserver
{
  private HashMap<String, GUser> users;
  private LinkedList<GMessage> messages;
  private String user;
  private LinkedList<GModelObserver> chatObs;
  private Gcom<GMessage> gcomMb;
  private Vector<ConnectionObserver> connectionObs;
  private String groupName;
  //private ChatState state; // TODO state pattern

  public Gchat ( String uid, String gid)
  {

    users = new HashMap<String, GUser>();
    messages = new LinkedList<GMessage>();

    user =  uid;
    users.put( uid, new GUser( user ) );
    groupName = gid;

    chatObs = new LinkedList<GModelObserver>();
    connectionObs = new Vector<ConnectionObserver>();

    try
    {
      gcomMb = GcomFactory.createGcom( OrderingStrategyEnum.CAUSAL, MulticastStrategyEnum.RELIABLE_MULTICAST );
      gcomMb.addObserver( this );
      gcomMb.connect();
    }
    catch ( RemoteException | NamingServiceUnavailableException e )
    {
      System.err.println( "Can't connect to the Naming Server..." );
      e.printStackTrace();
    }

  }

  public void setUserName( String newUID )
  {
    users.remove( user );
    user = newUID;
    users.put( newUID, new GUser( newUID ) );
  }

  public void connected ()
  {
    //gcomMb.connect();
    addUser( user );
    for ( ConnectionObserver observer : connectionObs )
      observer.connected( user );
  }

  public Collection<GUser> getUsers ()
  {
    return users.values();
  }

  public GUser getUser ( String uid )
  {
    return users.get( uid );
  }

  public LinkedList<GMessage> getMessages ()
  {
    return messages;
  }

  public String getUser ()
  {
    return user;
  }

  public String getUserName ()
  {
    return user;
  }

  public void addUser ( String newUser )
  {
    users.put( newUser, new GUser( newUser ) );
    for ( GModelObserver ob : chatObs )
      ob.newUser();
  }

  public void sendMessage ( String content )
  {
    gcomMb.send(new GTextMessage( user, content ) );
  }

  public void addObserver ( GModelObserver obs )
  {
    chatObs.add( obs );
  }

  @Override
  public void update ( Observable observable, Object o )
  {
    System.out.println( "update" );
    System.out.println( o.getClass() );
    System.out.println( observable.getClass() );
    System.out.println( o.toString() );
  }

  public String[] getAvailableGroups ()
  {
    return gcomMb.getGroups();
  }

  @Override
  public void newMessage ( Message message )
  {
    System.out.println( "Receiving " + message.getId() );
    GMessage msg = (GMessage) message.getContent();
    if ( msg.isJoinMessage() )
    {
      // broadcast my name
      if ( !( msg.getAuthor().equals(  user ) ) )
        gcomMb.send( new GJoinMessage( user, groupName ) );
      users.put( msg.getAuthor(), new GUser( msg.getAuthor() ) );
      for ( ConnectionObserver obs : connectionObs )
        obs.connected( msg.getAuthor() );
    }
    //if ( message.isMessage() )
    if ( msg.isTextMessage() )
    {
      messages.addLast( msg );
      for ( GModelObserver obs : chatObs )
      {
        obs.newMessage();
      }

    }
    System.out.println( "TODO" );
    System.out.println( message.toString() );
  }

  @Override
  public void join ( String groupID )
  {
    for ( ConnectionObserver obs : connectionObs )
      obs.connected( groupID );
  }

  public void join ( String userName, String group )
  {
    for ( ConnectionObserver obs : connectionObs )
      obs.connecting( userName );
    try
    {
      gcomMb.join( group );
      gcomMb.send( new GJoinMessage( userName, group ) );
      //connected();
    }
    catch ( CantJoinException e )
    {
      System.err.println( user + "can't join " + group );
      //e.printStackTrace();
      for ( ConnectionObserver obs : connectionObs )
        obs.disconnected( userName );
    }
  }

  public void addConnectionObserver ( ConnectionObserver connectionAgent )
  {
    connectionObs.add( connectionAgent );
  }

  public String getGroupName ()
  {
    return groupName;
  }

  public void setGroupName ( String groupName )
  {
    this.groupName = groupName;
  }
}

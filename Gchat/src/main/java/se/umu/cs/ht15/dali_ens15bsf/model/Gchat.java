package se.umu.cs.ht15.dali_ens15bsf.model;

import se.umu.cs.dist.ht15.dali_ens15bsf.com.BasicUnreliableMulticast;
import se.umu.cs.dist.ht15.dali_ens15bsf.groupmanagement.Member;
import se.umu.cs.dist.ht15.dali_ens15bsf.groupmanagement.MemberImpl;
import se.umu.cs.dist.ht15.dali_ens15bsf.ordering.CausalOrderer;

import java.rmi.RemoteException;
import java.util.*;

/**
 * Created by ens15bsf on 2015-10-28.
 * model class for the Gchat
 */
public class Gchat implements Observer
{
  private HashMap<String, GUser> users;
  private LinkedList<GMessage> messages;
  private GUser user;
  private LinkedList<GModelObserver> observers;

  private Member gcomMb;


  public Gchat ( String userName ) throws UnableToJoinException
  {
    users = new HashMap<String, GUser>();
    messages = new LinkedList<GMessage>();
    user = new GUser( userName );
    users.put( userName, user );

    observers = new LinkedList<GModelObserver>();

    try
    {
      gcomMb = new MemberImpl( new CausalOrderer(), new BasicUnreliableMulticast() );
    } catch ( RemoteException e )
    {
      throw new UnableToJoinException( e );
    }


  }

  public Collection<GUser> getUsers ()
  {
    return users.values();
  }

  public LinkedList<GMessage> getMessages ()
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
    messages.addLast( new GMessage( user, content ) );
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
    System.out.println(o.toString());
  }
}

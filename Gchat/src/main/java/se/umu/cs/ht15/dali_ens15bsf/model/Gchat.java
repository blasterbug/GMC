package se.umu.cs.ht15.dali_ens15bsf.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by ens15bsf on 2015-10-28.
 * model class for the Gchat
 */
public class Gchat
{
  private HashMap<String, GUser> users;
  private LinkedList<GMessage> messages;
  private GUser user;
  private LinkedList<GModelObserver> observers;


  public Gchat ( String userName )
  {
    users = new HashMap<String, GUser>();
    messages = new LinkedList<GMessage>();
    user = new GUser( userName );
    users.put( userName, user );

    observers = new LinkedList<GModelObserver>();

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
}

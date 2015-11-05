package se.umu.cs.dist.ht15.dali_ens15bsf;

import se.umu.cs.dist.ht15.dali_ens15bsf.com.MulticastStrategy;
import se.umu.cs.dist.ht15.dali_ens15bsf.groupmanagement.MemberImpl;
import se.umu.cs.dist.ht15.dali_ens15bsf.ordering.Orderer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

/**
 * Created by ens15bsf on 2015-11-05.
 */
public class Gcom<T> implements Observer
{
  private MemberImpl mbr;
  private Vector<GcomObserver> observers;

  public void Gcom( Orderer order, MulticastStrategy ms ) throws RemoteException
  {
    observers = new Vector<GcomObserver>();
    mbr = new MemberImpl( order, ms );
    mbr.addObserver( this );
  }

  public void connect() throws RemoteException, NotBoundException
  {
    mbr.connectToNameserver();
  }

  public void send( Message msg )
  {
    mbr.sendMessage( msg );
  }

  @Override
  public void update ( Observable observable, Object o )
  {
    Message msg = (Message)o;
    for ( GcomObserver obs : observers )
      obs.newMessage( msg );
  }

}

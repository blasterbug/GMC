package se.umu.cs.dist.ht15.dali_ens15bsf;

import se.umu.cs.dist.ht15.dali_ens15bsf.com.*;
import se.umu.cs.dist.ht15.dali_ens15bsf.groupmanagement.MemberImpl;
import se.umu.cs.dist.ht15.dali_ens15bsf.nameserver.NamingService;
import se.umu.cs.dist.ht15.dali_ens15bsf.nameserver.NamingServiceRemote;
import se.umu.cs.dist.ht15.dali_ens15bsf.ordering.FifoOrderer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by ens15bsf on 2015-10-12.
 */
public class groupRegisteringTest implements Observer
{


  public static void main ( String[] args )
  {
    String groupName = "TestGroup2";
    CommMember mbr1 = new CommMember( new TreeBaseMulticast() );
    mbr1.addObserver( new groupRegisteringTest() );
    try
    {
      Registry dictionary = LocateRegistry.getRegistry( NamingService.SERVER_PORT );
      NamingServiceRemote server = (NamingServiceRemote) dictionary.lookup( NamingService.SERVICE_NAME );
      LinkedList<String> groups = server.getGroups();
      if( !(groups.isEmpty()))
      {
        System.out.println( "Available groups : " );
        for ( String grp : groups )
          System.out.println( "\t- " + grp );
      }
      RemoteMember leader = server.joinGroup( "TestGroup", mbr1 );
      System.out.println(leader.toString());
      leader.deliver( new CommMessage<String>( new String("Hej Hej!") ) );
      //mbr.post( new CommMessage<String>( new String("Hej Hej!") ), server.getSharedObjects() ) ;
    } catch ( RemoteException e )
    {
      e.printStackTrace();
    } catch ( NotBoundException e )
    {
      e.printStackTrace();
    }
  }

  @Override
  public void update ( Observable observable, Object o )
  {
    System.out.println( observable.toString() + " : " + o.toString() );
  }
}

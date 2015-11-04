package se.umu.cs.dist.ht15.dali_ens15bsf;

import se.umu.cs.dist.ht15.dali_ens15bsf.com.*;
import se.umu.cs.dist.ht15.dali_ens15bsf.nameserver.NamingService;
import se.umu.cs.dist.ht15.dali_ens15bsf.nameserver.NamingServiceRemote;
import se.umu.cs.dist.ht15.dali_ens15bsf.groupmanagement.*;
import se.umu.cs.dist.ht15.dali_ens15bsf.ordering.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedList;

/**
 * Created by ens15bsf on 2015-10-12.
 * class to test naming server
 */
public class groupRegisteringTest implements ComObserver
{


  public static void main ( String[] args )
  {
    String groupName = "TestGroup";
    MulticastStrategy tree = new TreeBaseMulticast();
    Orderer causal = new CausalOrderer();
    
    try
    {
      ComObserver m = new MemberImpl(causal, tree);
      ComMember mbr1 = new ComMember( tree, m );
      mbr1.addObserver( new groupRegisteringTest() );
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
      leader.deliver( new ComMessage<String>( new String("Hej Hej!") ) );
      //mbr.post( new CommMessage<String>( new String("Hej Hej!") ), server.getSharedObjects() ) ;
    } catch ( RemoteException e )
    {
      e.printStackTrace();
    } catch ( NotBoundException e )
    {
      e.printStackTrace();
    }
  }


  /**
   * Notify Observers when a new incoming message arrive
   *
   * @param msg message to give to the Observer
   */
  @Override
  public void notifyObservers ( ComMessage msg )
  {
    System.out.println( msg.getSource().toString() + " : " + msg.getContent().toString() );
  }

  /**
   * Notify observer when new member want to join a group
   *
   * @param member  New member joining to the group
   * @param groupID Group name to join
   */
  @Override
  public void notifyNewMember ( RemoteMember member, String groupID )
  {
    System.out.println( member.toString() + " asked to join group " + groupID);
  }

  @Override
  public void notifyAddToView(RemoteMember m, String id) {

  }
}

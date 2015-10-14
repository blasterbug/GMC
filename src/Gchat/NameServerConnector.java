package Gchat;

import se.umu.cs.dist.ht15.dali_ens15bsf.com.RemoteMember;
import se.umu.cs.dist.ht15.dali_ens15bsf.nameserver.NamingService;
import se.umu.cs.dist.ht15.dali_ens15bsf.nameserver.NamingServiceRemote;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedList;

/**
 * Created by ens15bsf on 2015-10-13.
 * get connected to the Naming Service,
 * Register or joint groups there
 */
public class NameServerConnector
{
  private String groupName;
  private NamingServiceRemote namingServer;

  public NameServerConnector()
  {
    groupName = "chat1";
  }

  public void connect() throws RemoteException, NotBoundException
  {
    Registry dictionary = LocateRegistry.getRegistry( NamingService.SERVER_PORT );
    namingServer = (NamingServiceRemote) dictionary.lookup( NamingService.SERVICE_NAME );
  }

  public LinkedList<String> getGroups() throws RemoteException
  {

      return namingServer.getGroups();
  }

  public void registerGroup( String group ) throws RemoteException
  {
    LinkedList<String> groups = namingServer.getGroups();
    if( groups.contains( group ) )
    {
      RemoteMember leader = namingServer.getLeader( group );
      //leader.join(  );
    }
    //namingServer.joinGroup( group,   )
  }
}

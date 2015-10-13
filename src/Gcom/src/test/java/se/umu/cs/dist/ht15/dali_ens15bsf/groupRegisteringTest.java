package se.umu.cs.dist.ht15.dali_ens15bsf;

import se.umu.cs.dist.ht15.dali_ens15bsf.nameserver.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by ens15bsf on 2015-10-12.
 */
public class groupRegisteringTest
{
  public static void main( String[] args )
  {
    try
    {
      Registry dictionary = LocateRegistry.getRegistry( NamingService.SERVER_NAME, NamingService.SERVER_PORT );
      NamingServiceRemote server = (NamingServiceRemote) dictionary.lookup( NamingService.SERVER_NAME );
      System.out.println( server.getGroups() );
    }
    catch ( RemoteException e )
    {
      e.printStackTrace();
    } catch ( NotBoundException e )
    {
      e.printStackTrace();
    }
  }

}

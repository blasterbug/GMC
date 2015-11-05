package se.umu.cs.dist.ht15.dali_ens15bsf.nameserver;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by ens15bsf on 2015-11-05.
 */
public class NamingServerFabric
{

  private static NamingServiceRemote ns;

  private static void initNamingService()
  {
    Registry dictionary = null;
    try
    {
      dictionary = LocateRegistry.getRegistry( NamingService.SERVER_PORT );
      ns = (NamingServiceRemote) dictionary.lookup(NamingService.SERVICE_NAME);
    } catch ( RemoteException e )
    {
      e.printStackTrace();
    } catch ( NotBoundException e )
    {
      e.printStackTrace();
    }
  }

  public static NamingServiceRemote NamingService()
  {
    if ( null == ns )
      initNamingService();
    return ns;
  }
}

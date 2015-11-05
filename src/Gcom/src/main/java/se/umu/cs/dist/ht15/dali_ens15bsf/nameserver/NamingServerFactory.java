package se.umu.cs.dist.ht15.dali_ens15bsf.nameserver;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by ens15bsf on 2015-11-05.
 */
public class NamingServerFactory
{

  private static NamingServiceRemote ns;


  public static NamingServiceRemote NamingService() throws NamingServiceUnavailableException
  {
    if ( null == ns )
    {
      try
      {
        Registry dictionary = LocateRegistry.getRegistry( NamingService.SERVER_PORT );
        ns = (NamingServiceRemote) dictionary.lookup(NamingService.SERVICE_NAME);
      }
      catch ( RemoteException | NotBoundException e )
      {
        throw new NamingServiceUnavailableException( e.getMessage() );
      }
    }
    return ns;
  }
}

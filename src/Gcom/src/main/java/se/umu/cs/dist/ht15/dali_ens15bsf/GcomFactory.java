package se.umu.cs.dist.ht15.dali_ens15bsf;

import se.umu.cs.dist.ht15.dali_ens15bsf.com.BasicReliableMulticast;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.BasicUnreliableMulticast;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.MulticastStrategy;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.TreeBaseMulticast;
import se.umu.cs.dist.ht15.dali_ens15bsf.debug.gui.GcomDebugGUI;
import se.umu.cs.dist.ht15.dali_ens15bsf.nameserver.NamingServiceUnavailableException;
import se.umu.cs.dist.ht15.dali_ens15bsf.ordering.CausalOrderer;
import se.umu.cs.dist.ht15.dali_ens15bsf.ordering.FifoOrderer;
import se.umu.cs.dist.ht15.dali_ens15bsf.ordering.Orderer;
import se.umu.cs.dist.ht15.dali_ens15bsf.ordering.UnorderedStrategy;

import java.rmi.RemoteException;

/**
 * Created by ens15bsf on 2015-11-05.
 */

/**
 * Factory to build Gcom module
 */
public class GcomFactory
{
  /**
   * Construct a Gcom object
   * @param order Ordering strategy for the messages
   * @param mltStr Multicast strategy for the communication
   * @return Gcom module
   * @throws RemoteException
   */
  public static Gcom createGcom( OrderingStrategyEnum order, MulticastStrategyEnum mltStr ) throws NamingServiceUnavailableException, RemoteException
  {
    Orderer odr;
    switch ( order )
    {
      case FIFO:
        odr = new FifoOrderer();
        break;
      case CAUSAL:
        odr = new CausalOrderer();
        break;
      case UNORDERED:
        odr = new UnorderedStrategy();
        break;
      default:
        odr = new UnorderedStrategy();
        break;
    }
    MulticastStrategy str;
    switch ( mltStr )
    {
      case TREE_BASE:
        str = new TreeBaseMulticast();
        break;
      case RELIABLE_MULTICAST:
        str = new BasicReliableMulticast();
        break;
      case UNRELIABLE_MULTICAST:
        str = new BasicUnreliableMulticast();
        break;
      default:
        str = new BasicReliableMulticast();
        break;
    }
    return new GcomProxy( odr, str );
  }

  public static GcomDebug createGcomDebug( Gcom gcom ) throws NamingServiceUnavailableException, RemoteException
  {
    return new GcomDebug( gcom );
  }

  public static GcomDebug createGcomDebug( OrderingStrategyEnum order, MulticastStrategyEnum mltStr ) throws NamingServiceUnavailableException, RemoteException
  {
    return new GcomDebug( createGcom( order, mltStr ) );
  }

  public static GcomDebugGUI getDebugGui( Gcom module ) throws RemoteException, NamingServiceUnavailableException
  {
    return new GcomDebugGUI( createGcomDebug( module ) );
  }
}

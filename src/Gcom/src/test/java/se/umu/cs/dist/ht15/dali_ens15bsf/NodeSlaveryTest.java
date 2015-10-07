package se.umu.cs.dist.ht15.dali_ens15bsf;

import se.umu.cs.dist.ht15.dali_ens15bsf.com.CommManager;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.network.BasicNode;

import java.rmi.RemoteException;

/**
 * Created by ens15bsf on 2015-10-07.
 */
public class NodeSlaveryTest
{
  public static void main( String args[])
  {
    CommManager cm = new CommManager();
    try
    {
      BasicNode nd = new BasicNode( cm );
    }
    catch ( RemoteException e )
    {
      e.printStackTrace();
    }
  }
}

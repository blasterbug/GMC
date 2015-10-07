package se.umu.cs.dist.ht15.dali_ens15bsf;

import se.umu.cs.dist.ht15.dali_ens15bsf.com.network.NodeAlreadyBoundException;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.network.NodeMaster;

import java.rmi.RemoteException;

/**
 * Created by ens15bsf on 2015-10-07.
 */
public class NodeTest
{
  public static void main(String args[])
  {
    try
    {
      NodeMaster master = new NodeMaster();
    }
    catch ( Exception e )
    {
      e.printStackTrace();
    }

  }
}

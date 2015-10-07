package se.umu.cs.dist.ht15.dali_ens15bsf;



import se.umu.cs.dist.ht15.dali_ens15bsf.com.network.server.MasterNode;

import java.rmi.RemoteException;

/**
 * Created by ens15bsf on 2015-10-07.
 */
public class NodeMasterTest
{
  public static void main(String args[])
  {
    try
    {
      MasterNode nodeServer = new MasterNode();
    }
    catch ( Exception e )
    {
      e.printStackTrace();
    }

  }
}

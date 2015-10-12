package se.umu.cs.dist.ht15.dali_ens15bsf;



import se.umu.cs.dist.ht15.dali_ens15bsf.nameserver.NamingServer;

/**
 * Created by ens15bsf on 2015-10-07.
 */
public class RunNamingServer
{
  public static void main(String args[])
  {
    try
    {
      NamingServer nodeServer = new NamingServer();
    }
    catch ( Exception e )
    {
      e.printStackTrace();
    }

  }
}

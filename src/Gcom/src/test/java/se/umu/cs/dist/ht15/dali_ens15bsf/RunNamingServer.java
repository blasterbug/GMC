package se.umu.cs.dist.ht15.dali_ens15bsf;


import se.umu.cs.dist.ht15.dali_ens15bsf.nameserver.NamingService;

/**
 * Created by ens15bsf on 2015-10-07.
 */
public class RunNamingServer
{
  public static void main(String args[])
  {
    try
    {
      NamingService namingService = new NamingService();
    }
    catch ( Exception e )
    {
      e.printStackTrace();
    }

  }
}

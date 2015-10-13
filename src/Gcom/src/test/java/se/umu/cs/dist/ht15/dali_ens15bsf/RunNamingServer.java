package se.umu.cs.dist.ht15.dali_ens15bsf;



import se.umu.cs.dist.ht15.dali_ens15bsf.groupmanagement.MemberImpl;
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
      NamingService NamingService = new NamingService();
      NamingService.joinGroup( new MemberImpl( "test", new commMember() ) )
    }
    catch ( Exception e )
    {
      e.printStackTrace();
    }

  }
}

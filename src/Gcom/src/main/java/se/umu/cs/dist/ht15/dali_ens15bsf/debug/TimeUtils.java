package se.umu.cs.dist.ht15.dali_ens15bsf.debug;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ens15bsf on 08/12/15.
 */
public class TimeUtils
{
  static DateFormat formattor = new SimpleDateFormat("HH:mm:ss");

  public static String currentTime ()
  {
    return formattor.format( new Date() );
  }

  public static String getTime( long millis )
  {
    return formattor.format( new Date( millis ) );
  }
}

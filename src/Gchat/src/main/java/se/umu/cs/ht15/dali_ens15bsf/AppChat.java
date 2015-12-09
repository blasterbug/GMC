package se.umu.cs.ht15.dali_ens15bsf;

import se.umu.cs.dist.ht15.dali_ens15bsf.MulticastStrategyEnum;
import se.umu.cs.dist.ht15.dali_ens15bsf.OrderingStrategyEnum;
import se.umu.cs.ht15.dali_ens15bsf.model.Gchat;
import se.umu.cs.ht15.dali_ens15bsf.model.UnableToJoinException;
import se.umu.cs.ht15.dali_ens15bsf.view.ChatWindow;
import se.umu.cs.ht15.dali_ens15bsf.view.ConnectionWindow;

/**
 * Created by ens15bsf on 2015-10-13.
 */
public class AppChat
{

  public static void main ( String[] args ) throws UnableToJoinException
  {
    boolean debugApp = false;
    if ( 2 < args.length )
    {
      MulticastStrategyEnum multicast;
      OrderingStrategyEnum ordering;
      switch ( args[0] )
      {
        case "unreliable" :
          multicast = MulticastStrategyEnum.UNRELIABLE_MULTICAST;
          break;
        case "reliable" :
          multicast = MulticastStrategyEnum.RELIABLE_MULTICAST;
          break;
        case "tree" :
          multicast = MulticastStrategyEnum.TREE_BASE;
          break;
        default:
          multicast = MulticastStrategyEnum.RELIABLE_MULTICAST;
          System.exit( 1 );
          break;
      }
      switch ( args[1] )
      {
        case "unordered" :
          ordering = OrderingStrategyEnum.UNORDERED;
          break;
        case "causal" :
          ordering = OrderingStrategyEnum.CAUSAL;
          break;
        case "fifo" :
          ordering = OrderingStrategyEnum.FIFO;
          break;
        default:
          ordering = OrderingStrategyEnum.CAUSAL;
          System.exit( 1 );
          break;
      }
      if ( 3 < args.length )
      {
        if ( "debug".equals( args[3] ) )
        {
          System.out.println( " >> DEBUG MODE << " );
          debugApp = true;
        }
      }
      Gchat chatModel = new Gchat( RandomNames.getRandomUserName(), RandomNames.getRandomGroupName(), multicast, ordering, args[2], debugApp );
      ConnectionWindow connectionWindow = new ConnectionWindow( chatModel );
      ChatWindow chatView = new ChatWindow( chatModel );
      connectionWindow.setVisible( true );
    }
    else
      System.exit( 1 );
  }
}

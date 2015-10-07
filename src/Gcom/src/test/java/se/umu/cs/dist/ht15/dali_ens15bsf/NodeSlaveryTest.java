package se.umu.cs.dist.ht15.dali_ens15bsf;

import se.umu.cs.dist.ht15.dali_ens15bsf.com.CommListener;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.CommManager;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.CommMessage;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.network.BasicNode;

import java.rmi.RemoteException;

/**
 * Created by ens15bsf on 2015-10-07.
 */
public class NodeSlaveryTest implements CommListener
{
  public static void main( String args[])
  {
    CommManager cm = new CommManager();
    cm.register( new NodeSlaveryTest() );
    cm.send( new CommMessage( "message" ) );
  }


  /**
   * Notify a listener when new messages are ready
   * to be processed.
   */
  @Override
  public void deliver()
  {
  }

  /**
   * Add a message in the listener queue
   *
   * @param msg Message to queue
   */
  @Override
  public void queueCommMessage ( CommMessage msg )
  {
    System.out.println( msg.toString() );
  }
}

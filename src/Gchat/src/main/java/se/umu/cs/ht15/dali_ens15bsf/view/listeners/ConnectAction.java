package se.umu.cs.ht15.dali_ens15bsf.view.listeners;

import se.umu.cs.ht15.dali_ens15bsf.view.ConnectionWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ens15bsf on 2015-10-21.
 */
public class ConnectAction implements ActionListener
{
  private ConnectionWindow window;

  public ConnectAction ( ConnectionWindow wdw )
  {
    window = wdw;
  }

  @Override
  public void actionPerformed ( ActionEvent actionEvent )
  {
    // TODO
    System.out.println( window.getGroupConnection() );
    System.out.println( window.getUserName() );
  }
}

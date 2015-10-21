package Gchat.src.Listeners;

import Gchat.src.frames.ConnectionWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ens15bsf on 2015-10-21.
 */
public class ConnectAction implements ActionListener
{
  private ConnectionWindow window;

  public ConnectAction( ConnectionWindow wdw )
  {
    window = wdw;
  }
  @Override
  public void actionPerformed ( ActionEvent actionEvent )
  {
    System.out.println(window.getGroupConnection());
  }
}

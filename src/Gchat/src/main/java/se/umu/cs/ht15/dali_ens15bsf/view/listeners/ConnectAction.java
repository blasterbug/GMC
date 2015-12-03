package se.umu.cs.ht15.dali_ens15bsf.view.listeners;

import se.umu.cs.ht15.dali_ens15bsf.model.Gchat;
import se.umu.cs.ht15.dali_ens15bsf.view.ConnectionWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ens15bsf on 2015-10-21.
 */
public class ConnectAction implements ActionListener
{
  private ConnectionWindow window;
  private Gchat chat;

  public ConnectAction ( ConnectionWindow wdw, Gchat cht )
  {
    chat = cht;
    window = wdw;
  }

  @Override
  public void actionPerformed ( ActionEvent actionEvent )
  {
    String group = window.getGroupConnection();
    chat.setGroupName( window.getGroupConnection() );
    chat.setUserName( window.getUserName() );
    System.out.println( window.getUserName() + " wants to join " + group );
    chat.join( window.getUserName(), group );
  }
}

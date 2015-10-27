package Gchat.Listeners;

import Gchat.frames.ConnectionWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ens15bsf on 2015-10-27.
 */
public class AddGroupAction implements ActionListener
{
  private ConnectionWindow window;

  public AddGroupAction( ConnectionWindow wdw )
  {
    window = wdw;
  }
  @Override
  public void actionPerformed ( ActionEvent actionEvent )
  {
    window.addGroup( window.newGroupName() );
  }
}

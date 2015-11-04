package se.umu.cs.ht15.dali_ens15bsf.view.listeners;

import se.umu.cs.ht15.dali_ens15bsf.view.ConnectionWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ens15bsf on 2015-10-27.
 */
public class AddGroupAction implements ActionListener
{
  private ConnectionWindow window;

  public AddGroupAction ( ConnectionWindow wdw )
  {
    window = wdw;
  }

  @Override
  public void actionPerformed ( ActionEvent actionEvent )
  {
    if ( !( window.newGroupName().equals( ConnectionWindow.CREATE_GROUP ) ) )
      window.addGroup( window.newGroupName() );
  }
}

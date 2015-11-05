package se.umu.cs.ht15.dali_ens15bsf.view.listeners;

import se.umu.cs.ht15.dali_ens15bsf.model.Gchat;
import se.umu.cs.ht15.dali_ens15bsf.view.ConnectionWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ens15bsf on 2015-10-27.
 */
public class AddGroupAction implements ActionListener
{
  private ConnectionWindow window;
  private Gchat mdl;

  public AddGroupAction ( ConnectionWindow wdw, Gchat model )
  {
    window = wdw;
    mdl = model;
  }

  @Override
  public void actionPerformed ( ActionEvent actionEvent )
  {
    if ( !( window.newGroupName().equals( ConnectionWindow.CREATE_GROUP ) ) )
      mdl.createGroup( window.newGroupName() );
  }
}

package se.umu.cs.ht15.dali_ens15bsf.view.listeners;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Created by ens15bsf on 03/12/15.
 * Change switch state when the listened component get focus
 */
public class SwitchSelectedGroup implements FocusListener
{
  private FocusObserver obs;
  private boolean var;

  public SwitchSelectedGroup( FocusObserver observer, boolean switchState )
  {
    obs = observer;
    var = switchState;
  }
  @Override
  public void focusGained ( FocusEvent focusEvent )
  {
    obs.getFocus( var );
  }

  @Override
  public void focusLost ( FocusEvent focusEvent )
  {

  }
}

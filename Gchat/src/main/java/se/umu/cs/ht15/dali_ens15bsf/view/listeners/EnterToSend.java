package se.umu.cs.ht15.dali_ens15bsf.view.listeners;

import se.umu.cs.ht15.dali_ens15bsf.model.Gchat;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by ens15bsf on 2015-11-03.
 */
public class EnterToSend implements KeyListener
{

  private Gchat model;
  private JTextArea input;

  public EnterToSend( Gchat chat, JTextArea messageInput )
  {
    super();
    model = chat;
    input = messageInput;
  }
  @Override
  public void keyTyped ( KeyEvent keyEvent )
  {

  }

  @Override
  public void keyPressed ( KeyEvent keyEvent )
  {
    if ( keyEvent.getKeyCode() == KeyEvent.VK_ENTER )
    {
      model.sendMessage( input.getText() );
      input.setText( "" );
    }

  }

  @Override
  public void keyReleased ( KeyEvent keyEvent )
  {

  }
}

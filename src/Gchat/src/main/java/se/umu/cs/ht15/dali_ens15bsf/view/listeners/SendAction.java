package se.umu.cs.ht15.dali_ens15bsf.view.listeners;

import se.umu.cs.ht15.dali_ens15bsf.model.Gchat;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ens15bsf on 2015-10-21.
 */
public class SendAction implements ActionListener
{

  private Gchat model;
  private JTextArea input;

  public SendAction ( Gchat chat, JTextArea messageInput )
  {
    super();
    model = chat;
    input = messageInput;
  }

  @Override
  public void actionPerformed ( ActionEvent actionEvent )
  {
    model.sendMessage( input.getText() );
    input.setText( "" );
  }
}

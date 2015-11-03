package se.umu.cs.ht15.dali_ens15bsf.model;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ens15bsf on 2015-10-28.
 */
public class GMessage extends JPanel
{
  private GUser from;
  private String content;


  public GMessage ( GUser user, String msg )
  {
    super();
    from = user;
    content = msg;

    GroupLayout layout = new GroupLayout( this );
    setLayout( layout );
    JTextArea username = new JTextArea( from.toString() );
    username.setEditable( false );
    username.setLayout( new BorderLayout() );
    JTextArea message = new JTextArea( msg );
    message.setEditable( false );
    message.setLineWrap( true );
    message.setLayout( new BorderLayout() );
    layout.setAutoCreateGaps( true );
    layout.setAutoCreateContainerGaps( true );
    layout.setHorizontalGroup(
            layout.createSequentialGroup()
                    .addComponent( username )
                    .addGap( 5 )
                    .addComponent( message )
    );
    layout.setVerticalGroup(
            layout.createSequentialGroup()
                    .addGroup( layout.createParallelGroup( GroupLayout.Alignment.BASELINE )
                            .addComponent( username )
                            .addGap( 5 )
                            .addComponent( message ) )
    );


//    Color backgroundColor = user.getColor().brighter().brighter();
//    this.setBackground( backgroundColor );
//    username.setBackground( backgroundColor );
//    message.setBackground( backgroundColor );
    Color foregroundColor = user.getColor().darker();
    this.setForeground( foregroundColor );
    username.setForeground( user.getColor() );
    message.setForeground( foregroundColor );
    setBackground( username.getBackground() );
    setSize( new Dimension( Short.MAX_VALUE, Short.MAX_VALUE ) );

  }

  public String toString ()
  {
    return from + " said " + content;
  }
}
package se.umu.cs.ht15.dali_ens15bsf.model;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ens15bsf on 2015-10-28.
 */
public class GMessageDisplay extends JPanel
{
  private GUser from;
  private String content;


  public GMessageDisplay ( GUser user, String msg )
  {
    super();
    from = user;
    content = msg;

    GroupLayout layout = new GroupLayout( this );
    setLayout( layout );
    JTextArea username = new JTextArea( from.toString() );
    username.setEditable( false );
    username.setLayout( new BoxLayout( username, BoxLayout.PAGE_AXIS ) );
    JTextArea message = new JTextArea( msg );
    message.setEditable( false );
    message.setWrapStyleWord( true );
    message.setLineWrap( true );
    message.setLayout( new BoxLayout( message, BoxLayout.PAGE_AXIS ) );
    layout.setAutoCreateGaps( true );
    layout.setAutoCreateContainerGaps( true );
    layout.setHorizontalGroup(
            layout.createSequentialGroup()
                    .addComponent( username, 100, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE )
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
    setForeground( foregroundColor );
    username.setForeground( user.getColor() );
    message.setForeground( foregroundColor );
    setBackground( username.getBackground() );
    setBorder( BorderFactory.createLineBorder( foregroundColor ) );
    //setSize( new Dimension( Short.MAX_VALUE, Short.MAX_VALUE ) );
  }

  public String toString ()
  {
    return from + " said " + content;
  }
}

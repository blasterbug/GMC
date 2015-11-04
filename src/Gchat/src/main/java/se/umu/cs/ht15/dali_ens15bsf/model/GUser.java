package se.umu.cs.ht15.dali_ens15bsf.model;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Created by ens15bsf on 2015-10-28.
 */
public class GUser extends JLabel
{

  private static Random rd = new Random();


  private String name;
  private Color color;

  private static Color chooseColor ()
  {
    return new Color( rd.nextInt( 250 ), rd.nextInt( 250 ), rd.nextInt( 250 ) );
  }

  public GUser ( String name )
  {
    super( name );
    this.name = name;
    setLayout( new BoxLayout(this, BoxLayout.PAGE_AXIS) );
    color = chooseColor();
    setBackground( color.brighter() );
    setForeground( color.darker() );
    //setBorder( BorderFactory.createLineBorder( color ) );
    //setSize( 20, 300 );
  }

  public Color getColor ()
  {
    return color;
  }

  public String getName ()
  {
    return name;
  }

  public String toString ()
  {
    return name;
  }

}

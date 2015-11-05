package se.umu.cs.ht15.dali_ens15bsf.model;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.Random;

/**
 * Created by ens15bsf on 2015-10-28.
 */
public class GUserDisplay extends JLabel implements Serializable
{

  private static final long serialVersionUID = -6777933872396080915L;
  private static Random rd = new Random();


  private String name;
  private Color color;

  public GUserDisplay ( String name )
  {
    super( name );
    this.name = name;
    setLayout( new BoxLayout( this, BoxLayout.PAGE_AXIS ) );
    color = chooseColor();
    setBackground( color.brighter() );
    setForeground( color.darker() );
    //setBorder( BorderFactory.createLineBorder( color ) );
    //setSize( 20, 300 );
  }

  private static Color chooseColor ()
  {
    return new Color( rd.nextInt( 250 ), rd.nextInt( 250 ), rd.nextInt( 250 ) );
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

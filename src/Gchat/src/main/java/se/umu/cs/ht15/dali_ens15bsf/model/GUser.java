package se.umu.cs.ht15.dali_ens15bsf.model;

import java.awt.*;
import java.io.Serializable;
import java.util.Random;

/**
 * Created by ens15bsf on 2015-10-28.
 */
public class GUser implements Serializable
{

  private static final long serialVersionUID = -6777933872396080915L;
  private static Random rd = new Random();


  private String gname;
  private Color color;

  public GUser ( String name )
  {
    super();
    this.gname = name;
    color = chooseColor();
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
    return gname;
  }

  public String toString ()
  {
    return gname;
  }

  public void setUID ( String UID )
  {
    this.gname = UID;
  }
}

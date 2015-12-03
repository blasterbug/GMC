package se.umu.cs.ht15.dali_ens15bsf.view;

import se.umu.cs.ht15.dali_ens15bsf.model.GUser;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ens15bsf on 03/12/15.
 */
public class GUserListCellRender implements ListCellRenderer<GUser>
{
  @Override
  public Component getListCellRendererComponent ( JList<? extends GUser> jList, GUser gUser, int i, boolean b, boolean b1 )
  {
    JLabel cell = new JLabel( gUser.getName() );
    Color color = gUser.getColor();
    cell.setLayout( new BoxLayout( cell , BoxLayout.PAGE_AXIS ) );
    //cell.setBackground( color.brighter() );
    cell.setForeground( color );
    //cell.setBorder( BorderFactory.createLineBorder( color ) );
    return cell;
  }
}

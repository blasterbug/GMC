package se.umu.cs.ht15.dali_ens15bsf.model;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.util.Vector;

/**
 * Created by ens15bsf on 03/12/15.
 */
public class UserListModel implements ListModel<GUser>
{
  private Vector<GUser> users;
  private Vector<ListDataListener> listDataListeners;

  public UserListModel()
  {
    users = new Vector<>();
    listDataListeners = new Vector<>();
  }

  @Override
  public int getSize ()
  {
    return users.size();
  }

  @Override
  public GUser getElementAt ( int i )
  {
    return users.get( i );
  }

  @Override
  public void addListDataListener ( ListDataListener listDataListener )
  {
    listDataListeners.add( listDataListener );
  }

  @Override
  public void removeListDataListener ( ListDataListener listDataListener )
  {
    listDataListeners.remove( listDataListener );
  }

  public void addUser( GUser user )
  {
    users.add( user );
  }

  public void RemoveUser( GUser user )
  {
    users.remove( user );
  }

  public void removeAll ()
  {
    users.removeAllElements();
  }
}

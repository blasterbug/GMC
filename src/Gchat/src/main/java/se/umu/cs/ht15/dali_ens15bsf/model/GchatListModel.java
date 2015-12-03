package se.umu.cs.ht15.dali_ens15bsf.model;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.util.Vector;

/**
 * Created by ens15bsf on 03/12/15.
 */
public class GchatListModel<T> implements ListModel
{
  private Vector<T> list;
  private Vector<ListDataListener> listDataListeners;

  public GchatListModel ()
  {
    list = new Vector<>();
    listDataListeners = new Vector<>();
  }

  @Override
  public int getSize ()
  {
    return list.size();
  }

  @Override
  public T getElementAt ( int i )
  {
    return list.get( i );
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

  public void addElement( T item )
  {
    list.add( item );
  }

  public void RemoveUser( T item )
  {
    list.remove( item );
  }

  public void removeAll ()
  {
    list.removeAllElements();
  }
}

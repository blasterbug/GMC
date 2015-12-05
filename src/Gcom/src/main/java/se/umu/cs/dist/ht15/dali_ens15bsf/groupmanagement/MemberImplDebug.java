package se.umu.cs.dist.ht15.dali_ens15bsf.groupmanagement;

import se.umu.cs.dist.ht15.dali_ens15bsf.com.ComMember;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.MulticastStrategy;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.debug.ComMemberDebug;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.debug.ComMemberDebugObserver;
import se.umu.cs.dist.ht15.dali_ens15bsf.ordering.Orderer;

import java.rmi.RemoteException;

/**
 * Created by benjamin on 05/12/15.
 */
public class MemberImplDebug extends MemberImpl
{
  public MemberImplDebug( Orderer o, MulticastStrategy strg ) throws RemoteException
  {
    super( o, strg );
    self = new ComMemberDebug(strg, this);
  }

  public void addObserverComMemberDebug( ComMemberDebugObserver obs )
  {
    ((ComMemberDebug)self).addObserver( obs );
  }

  public void removeObserverComMemberDebug( ComMemberDebugObserver obs )
  {
    ((ComMemberDebug)self).removeObserver( obs );
  }
}

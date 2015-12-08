package se.umu.cs.dist.ht15.dali_ens15bsf.groupmanagement;

import se.umu.cs.dist.ht15.dali_ens15bsf.com.ComMember;
import se.umu.cs.dist.ht15.dali_ens15bsf.ordering.Orderer;

import java.rmi.RemoteException;

/**
 * Created by ens15bsf on 08/12/15.
 */
public class MemberImplDebug extends MemberImpl
{
  public MemberImplDebug ( Orderer o, ComMember comMbr ) throws RemoteException
  {
    super(o, comMbr);
  }
}

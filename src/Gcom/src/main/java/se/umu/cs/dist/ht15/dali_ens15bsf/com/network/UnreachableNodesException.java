package se.umu.cs.dist.ht15.dali_ens15bsf.com.network;

import java.util.Collection;

/**
 * Created by ens15bsf on 2015-10-07.
 */
public class UnreachableNodesException extends Exception
{
  private final Collection<String> unreachableNodes;

  public UnreachableNodesException( Collection<String> unreachableNodesList )
  {
    super();
    this.unreachableNodes = unreachableNodesList;
  }

  public Collection<String> getUnreachableNodes()
  {
    return unreachableNodes;
  }
}

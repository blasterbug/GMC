package se.umu.cs.dist.ht15.dali_ens15bsf.nameserver;

import java.util.Collection;
import se.umu.cs.dist.ht15.dali_ens15bsf.groupmanagement.Member;

public interface NameServer {
	public Member joinGroup(String groupname, Member m);
	public Collection<String> getGroups();
}

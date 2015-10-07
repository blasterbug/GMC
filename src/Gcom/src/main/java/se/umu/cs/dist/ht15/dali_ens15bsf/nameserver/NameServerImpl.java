package se.umu.cs.dist.ht15.dali_ens15bsf.nameserver;

import se.umu.cs.dist.ht15.dali_ens15bsf.nameserver.NameServer;
import se.umu.cs.dist.ht15.dali_ens15bsf.groupmanagement.Member;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

public class NameServerImpl implements NameServer {
	private Map<String, Member> groups;

	public NameServerImpl() {
		groups = new HashMap<String, Member>();
	}

	@Override
	public Member joinGroup(String groupname, Member m) {
		if (!groups.containsKey(groupname))
			groups.put(groupname, m);
		return groups.get(groupname);
	}

	@Override
	public Collection<String> getGroups() {
		return groups.keySet();
	}

}

package se.umu.cs.dist.ht15.dali_ens15bsf.groupmanagement;

import se.umu.cs.dist.ht15.dali_ens15bsf.groupmanagement.Member;
import se.umu.cs.dist.ht15.dali_ens15bsf.Message;

import java.util.Map;
import java.util.HashMap;

public class MemberImpl implements Member {
	private Map<String, Member> members;

	public MemberImpl() {
		members = new HashMap<String, Member>();
	}

	@Override
	public void join(Member m, String id) {
		members.put(id, m);

	}

	@Override
	public void sendMessage(Message m) {


	}

	@Override
	public void receiveMessage(Message m) {

	}

}

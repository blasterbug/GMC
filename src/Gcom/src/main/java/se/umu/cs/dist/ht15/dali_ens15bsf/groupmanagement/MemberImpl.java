package se.umu.cs.dist.ht15.dali_ens15bsf.groupmanagement;

import se.umu.cs.dist.ht15.dali_ens15bsf.groupmanagement.Member;
import se.umu.cs.dist.ht15.dali_ens15bsf.Message;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.MemberRemote;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.CommMessage;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.CommMember;
import se.umu.cs.dist.ht15.dali_ens15bsf.ordering.Orderer;

import java.util.Map;
import java.util.HashMap;

public class MemberImpl implements Member {
	private Map<String, MemberRemote> view;
	private Orderer orderer;
	private MemberRemote self;

	public MemberImpl(Orderer o) {
		view = new HashMap<String, MemberRemote>();
		orderer = o;
		self = new CommMember(this, ""/*TODO*/, null);
	}

	@Override
	public void join(MemberRemote m, String id) {
		view.put(id, m);

	}

	/**
	  * @param Message A message to send to the group
	  */
	@Override
	public void sendMessage(Message m) {
		/* Prepare according to orderer */
		Message preparedMessage = orderer.prepareMessage(m);
		
		/* Create communication message */
		CommMessage<Message> msg;
		msg = new CommMessage(preparedMessage);

		/* Send message to view */
		//self.post(msg, view);


	}

	@Override
	public void receiveMessage(Message m) {

	}

}

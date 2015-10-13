package se.umu.cs.dist.ht15.dali_ens15bsf;

import junit.framework.Assert;
import org.junit.Test;

import java.util.Collection;
import java.rmi.RemoteException;

import se.umu.cs.dist.ht15.dali_ens15bsf.com.MulticastStrategy;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.BasicUnreliableMulticast;
import se.umu.cs.dist.ht15.dali_ens15bsf.ordering.CausalOrderer;
import se.umu.cs.dist.ht15.dali_ens15bsf.ordering.Orderer;
import se.umu.cs.dist.ht15.dali_ens15bsf.groupmanagement.Member;
import se.umu.cs.dist.ht15.dali_ens15bsf.groupmanagement.MemberImpl;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.RemoteMember;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.CommMessage;

public class MemberTest {


	@Test
	public void shouldCreateMemberWithOrdererAndMulticast() {
		Orderer causal = new CausalOrderer();
		MulticastStrategy strg = new BasicUnreliableMulticast();
		Member m = new MemberImpl(causal, strg);
	}

	@Test
	public void shouldAddNewMemberToView() {
		Orderer causal = new CausalOrderer();
		MulticastStrategy strg = new BasicUnreliableMulticast();
		Member m = new MemberImpl(causal, strg);

		Orderer causal2 = new CausalOrderer();
		MulticastStrategy strg2 = new BasicUnreliableMulticast();

		Member m2 = new MemberImpl(causal2, strg2);


		m.join(m2.getRemoteMember(), "id1");

		Assert.assertTrue(m.getView().contains(m2.getRemoteMember()));

	}

	@Test
	public void shouldCreateRemoteMember() {
		Orderer causal = new CausalOrderer();
		MulticastStrategy strg = new BasicUnreliableMulticast();
		Member m = new MemberImpl(causal, strg);

		Assert.assertTrue(m.getRemoteMember() !=null);
	}

	@Test
	public void shouldReceiveMessage() {
		Orderer causal = new CausalOrderer();
		MulticastStrategy strg = new BasicUnreliableMulticast();

		Member m = new MemberImpl(causal, strg);

		CommMessage<Message> msg = new CommMessage<Message>(new Message("id1", "m1"));

		try{
			strg.receive(msg);
		}catch(RemoteException exp) {
			Assert.fail();
		}
	}
}

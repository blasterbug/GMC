package se.umu.cs.dist.ht15.dali_ens15bsf.com;

import junit.framework.Assert;
import org.junit.Test;
import se.umu.cs.dist.ht15.dali_ens15bsf.Message;
import se.umu.cs.dist.ht15.dali_ens15bsf.groupmanagement.Member;
import se.umu.cs.dist.ht15.dali_ens15bsf.groupmanagement.MemberImpl;
import se.umu.cs.dist.ht15.dali_ens15bsf.ordering.CausalOrderer;
import se.umu.cs.dist.ht15.dali_ens15bsf.ordering.Orderer;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

public class MemberTest {


	private class DummyObserver implements Observer {
		private LinkedList<Message> messages;

		public DummyObserver() {
			messages = new LinkedList<Message>();
		}

		@Override
		public void update(Observable obs, Object o) {
			Message msg = (Message)o;
			messages.add((Message)o);
		}

		public boolean contains(Message m) {
			return messages.contains(m);
		}

		public boolean containsAt(Message m, int i) {
			if(messages.size() > i) 
				return messages.get(i).getId().equals(m.getId()) && 
					messages.get(i).getContent().equals(m.getContent());
			return false;
		}

		public void printMessages() {
			int i = 0;
			System.out.println("PRINTING DUMMY MESSAGES");
			for ( Message m : messages) {
				System.out.println("Message ["+i++ +"]: "+m.getContent());
			}
		}
	}

	@Test
	public void shouldCreateMemberWithOrdererAndMulticast() {
		try{	
			Orderer causal = new CausalOrderer();
			MulticastStrategy strg = new BasicUnreliableMulticast();
			ComMember cm = new ComMember( strg, "id" );
			Member m = new MemberImpl(causal, cm);
			cm.addObserver( m );
		}catch(RemoteException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void shouldAddNewMemberToView() {
		try{
			Orderer causal = new CausalOrderer();
			MulticastStrategy strg = new BasicUnreliableMulticast();
			ComMember cm1 = new ComMember( strg, "m1" );
			Member m1 = new MemberImpl(causal, cm1);
			cm1.addObserver( m1 );
	
			Orderer causal2 = new CausalOrderer();
			MulticastStrategy strg2 = new BasicUnreliableMulticast();

			ComMember cm2 = new ComMember( strg2, "m2" );
			Member m2 = new MemberImpl(causal2, cm2);
			cm2.addObserver( m2 );
			m1.join(m2.getRemoteMember(), "id1");
	
			Assert.assertTrue(m1.getView().contains(m2.getRemoteMember()));
		}catch(RemoteException e) {
			Assert.fail(e.getMessage());
		}

	}

	@Test
	public void shouldCreateRemoteMember() {
		try{
			Orderer causal = new CausalOrderer();
			MulticastStrategy strg = new BasicUnreliableMulticast();
			ComMember cm = new ComMember( strg, "m0" );
			Member m = new MemberImpl(causal, cm);
			cm.addObserver( m );

			Assert.assertTrue(m.getRemoteMember() !=null);
		}catch(RemoteException e) {
			Assert.fail(e.getMessage());
		}

	}

	@Test
	public void shouldReceiveMessage() {
		try{
			Orderer causal = new CausalOrderer();
			MulticastStrategy strg = new BasicUnreliableMulticast();
			ComMember cm = new ComMember( strg, "id" );
			Member m = new MemberImpl(causal, cm);
			cm.addObserver( m );

			ComMessage<Message> msg = new ComMessage<Message>(causal.prepareMessage(new Message("id1", "m1")));
			msg.setSource(m.getRemoteMember());
			strg.receive(msg);
		}catch(RemoteException | UnreachableRemoteObjectException exp) {
			exp.printStackTrace();
			Assert.fail(exp.getMessage());
		}
	}

	@Test
	public void shouldSendToRemoteMember() {
		try {
			Orderer causal = new CausalOrderer();
			MulticastStrategy strg = new BasicUnreliableMulticast();
			ComMember cm1 = new ComMember( strg, "id1" );
			Member m1 = new MemberImpl(causal, cm1);
			cm1.addObserver( m1 );
			m1.setId("id1");

			Orderer causal2 = new CausalOrderer();
			MulticastStrategy strg2 = new BasicUnreliableMulticast();
			ComMember cm2 = new ComMember( strg2, "id2" );
			Member m2 = new MemberImpl(causal2, cm2);
			cm2.addObserver( m2 );
			m2.setId("id2");
	
			Message msg1 = new Message("id1", "test message");
			m1.join(m2.getRemoteMember(), "id2");


			m1.sendMessage(msg1);
			// TODO LISTENER
		}catch(RemoteException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}

	}

	@Test
	public void shouldDeliverMessagesInOrder() {
		try{
			Orderer c1 = new CausalOrderer();
			Orderer c2 = new CausalOrderer();
			Orderer c3 = new CausalOrderer();
			Orderer c4 = new CausalOrderer();

			MulticastStrategy s1 = new BasicUnreliableMulticast();
			MulticastStrategy s2 = new BasicUnreliableMulticast();
			MulticastStrategy s3 = new BasicUnreliableMulticast();
			MulticastStrategy s4 = new BasicUnreliableMulticast();

			ComMember cm1 = new ComMember( s1, "id1" );
			ComMember cm2 = new ComMember( s2, "id2" );
			ComMember cm3 = new ComMember( s3, "id3" );
			ComMember cm4 = new ComMember( s4, "id4" );

			MemberImpl m1 = new MemberImpl(c1, cm1);
			cm1.addObserver( m1 );
			Member m2 = new MemberImpl(c2, cm2);
			cm2.addObserver( m2 );
			Member m3 = new MemberImpl(c3, cm3);
			cm3.addObserver( m3 );
			Member m4 = new MemberImpl(c4, cm4);
			cm4.addObserver( m4 );

			m1.setId("id1");
			m2.setId("id2");
			m3.setId("id3");
			m4.setId("id4");

			m1.join(m1.getRemoteMember(), "id1");
			m1.join(m2.getRemoteMember(), "id2");
			m1.join(m3.getRemoteMember(), "id3");
			m1.join(m4.getRemoteMember(), "id4");

			Message msg1 = new Message("id1", "test1");
			Message msg2 = new Message("id2", "test2");
			Message msg3 = new Message("id3", "test3");

			DummyObserver dummy = new DummyObserver();
			m1.addObserver(dummy);

			m1.sendMessage("test1");
			m2.sendMessage("test2");
			m3.sendMessage("test3");

			Assert.assertTrue(dummy.containsAt(msg1, 0) 
					&& dummy.containsAt(msg2, 1) 
					&& dummy.containsAt(msg3, 2));

		}catch(RemoteException e) {
			Assert.fail(e.getMessage());
		}

	}

}

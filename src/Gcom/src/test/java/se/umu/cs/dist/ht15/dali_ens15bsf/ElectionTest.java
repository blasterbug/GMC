package se.umu.cs.dist.ht15.dali_ens15bsf;

import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import se.umu.cs.dist.ht15.dali_ens15bsf.ordering.*;
import se.umu.cs.dist.ht15.dali_ens15bsf.nameserver.*;
import se.umu.cs.dist.ht15.dali_ens15bsf.groupmanagement.*;
import se.umu.cs.dist.ht15.dali_ens15bsf.com.*;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;


public class ElectionTest {
	NamingService ns;

	private class DummyObserver implements Observer {
		private LinkedList<CausalMessage> messages;

		public DummyObserver() {
			messages = new LinkedList<CausalMessage>();
		}

		@Override
		public void update(Observable obs, Object o) {
			messages.add((CausalMessage)o);
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

	@Before
	public void initNameserver()  {
		try {
			NamingService ns = new NamingService();
		}catch(Exception e) {
			System.err.println( "Couldn't start naming service" );
			System.exit( 1 );
		}

	}

	@After
	public void destroyNameserver() {
		try {
			ns.destroy();
		} catch (Exception e) {
			Assert.fail();
		}
	}

	@Ignore
	@Test
	public void testDisconnectingLeader() {
		try {
			Member lead = new MemberImpl(new UnorderedStrategy(), new BasicReliableMulticast());
			Member m1 = new MemberImpl(new UnorderedStrategy(), new BasicReliableMulticast());
			Member m2 = new MemberImpl(new UnorderedStrategy(), new BasicReliableMulticast());

			lead.connectToNameserver();
			m1.connectToNameserver();
			m2.connectToNameserver();

			lead.updateIdFromNameServer();
			m1.updateIdFromNameServer();
			m2.updateIdFromNameServer();
		} catch ( Exception e) {
			Assert.fail();
		}


	}
}
